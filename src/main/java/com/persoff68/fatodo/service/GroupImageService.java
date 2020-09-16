package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.model.Image;
import com.persoff68.fatodo.repository.GroupImageRepository;
import com.persoff68.fatodo.service.exception.ModelAlreadyExistsException;
import com.persoff68.fatodo.service.exception.ModelNotFoundException;
import com.persoff68.fatodo.service.util.ImageUtils;
import com.persoff68.fatodo.service.util.ResizeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupImageService {
    private static final String PREFIX = "group-";
    private static final String POSTFIX = ".jpg";

    private final GroupImageRepository groupImageRepository;

    public GroupImage getByFilename(String filename) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        return groupImageRepository.findByFilename(filename)
                .orElseThrow(ModelNotFoundException::new);
    }

    public String create(Image image) {
        if (image.getFilename() != null) {
            throw new ModelAlreadyExistsException();
        }
        BufferedImage bufferedImage = validateAndConvert(image.getContent());
        String filename = PREFIX + UUID.randomUUID() + POSTFIX;
        byte[] content = ResizeUtils.getOriginal(bufferedImage);
        byte[] thumbnail = ResizeUtils.getThumbnail(bufferedImage);
        GroupImage groupImage = new GroupImage(filename, content, thumbnail);
        groupImageRepository.save(groupImage);
        return filename;
    }

    public String update(Image image) {
        if (image.getFilename() == null) {
            throw new ModelNotFoundException();
        }
        GroupImage groupImage = groupImageRepository.findByFilename(image.getFilename())
                .orElseThrow(ModelNotFoundException::new);

        BufferedImage bufferedImage = validateAndConvert(image.getContent());
        byte[] content = ResizeUtils.getOriginal(bufferedImage);
        byte[] thumbnail = ResizeUtils.getThumbnail(bufferedImage);
        groupImage.setContent(content);
        groupImage.setThumbnail(thumbnail);
        groupImageRepository.save(groupImage);
        return image.getFilename();
    }

    public void delete(String filename) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        groupImageRepository.deleteByFilename(filename);
    }

    private BufferedImage validateAndConvert(MultipartFile file) {
        InputStream inputStream = ImageUtils.getInputStreamFromFile(file);
        ImageUtils.validateGroupImage(inputStream);
        return ImageUtils.getBufferedImage(inputStream);
    }

}
