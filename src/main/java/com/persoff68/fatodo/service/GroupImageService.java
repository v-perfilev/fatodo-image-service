package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.repository.GroupImageRepository;
import com.persoff68.fatodo.service.exception.ModelNotFoundException;
import com.persoff68.fatodo.service.util.ImageUtils;
import com.persoff68.fatodo.service.util.ResizeUtils;
import com.persoff68.fatodo.service.validator.ImageValidator;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class GroupImageService implements StoreService {
    public static final String PREFIX = "group-";

    private final GroupImageRepository groupImageRepository;

    public GroupImage getByFilename(String filename) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        return groupImageRepository.findByFilename(filename)
                .orElseThrow(ModelNotFoundException::new);
    }

    public String create(byte[] image) {
        ImageValidator.validateGroupImage(image);
        BufferedImage bufferedImage = ImageUtils.getBufferedImage(image);

        String filename = ImageUtils.generateFilename(PREFIX);
        Binary content = ResizeUtils.getOriginal(bufferedImage);
        Binary thumbnail = ResizeUtils.getThumbnail(bufferedImage);

        GroupImage groupImage = new GroupImage(filename, content, thumbnail);
        groupImageRepository.save(groupImage);

        return filename;
    }

    public String update(String filename, byte[] image) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        GroupImage groupImage = groupImageRepository.findByFilename(filename)
                .orElseThrow(ModelNotFoundException::new);

        ImageValidator.validateGroupImage(image);
        BufferedImage bufferedImage = ImageUtils.getBufferedImage(image);

        Binary content = ResizeUtils.getOriginal(bufferedImage);
        Binary thumbnail = ResizeUtils.getThumbnail(bufferedImage);

        groupImage.setContent(content);
        groupImage.setThumbnail(thumbnail);
        groupImageRepository.save(groupImage);

        return filename;
    }

    public void delete(String filename) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        groupImageRepository.deleteByFilename(filename);
    }


}
