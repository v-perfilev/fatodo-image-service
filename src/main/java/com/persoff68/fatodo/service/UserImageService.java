package com.persoff68.fatodo.service;

import com.persoff68.fatodo.model.UserImage;
import com.persoff68.fatodo.repository.UserImageRepository;
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
public class UserImageService implements StoreService {
    public static final String PREFIX = "user-";

    private final UserImageRepository userImageRepository;

    public UserImage getByFilename(String filename) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        return userImageRepository.findByFilename(filename)
                .orElseThrow(ModelNotFoundException::new);
    }

    public String create(byte[] image) {
        validateUserImage(image);
        BufferedImage bufferedImage = ImageUtils.getBufferedImage(image);

        String filename = ImageUtils.generateFilename(PREFIX);
        Binary content = ResizeUtils.getOriginal(bufferedImage);
        Binary thumbnail = ResizeUtils.getThumbnail(bufferedImage);

        UserImage userImage = new UserImage(filename, content, thumbnail);
        userImageRepository.save(userImage);

        return filename;
    }

    public String update(String filename, byte[] image) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        UserImage userImage = userImageRepository.findByFilename(filename)
                .orElseThrow(ModelNotFoundException::new);

        validateUserImage(image);
        BufferedImage bufferedImage = ImageUtils.getBufferedImage(image);

        Binary content = ResizeUtils.getOriginal(bufferedImage);
        Binary thumbnail = ResizeUtils.getThumbnail(bufferedImage);

        userImage.setContent(content);
        userImage.setThumbnail(thumbnail);
        userImageRepository.save(userImage);

        return filename;
    }

    public void delete(String filename) {
        if (filename == null) {
            throw new ModelNotFoundException();
        }
        userImageRepository.deleteByFilename(filename);
    }

    private static void validateUserImage(byte[] bytes) {
        BufferedImage bufferedImage = ImageUtils.getBufferedImage(bytes);
        ImageValidator validator = new ImageValidator(bufferedImage, bytes);
        validator.validateExtension("image/jpeg");
        validator.validateDimensions(100, 500, 100, 500);
        validator.validateRatio(1, 1);
        validator.validateSize(1024, 1024 * 512);
    }

}
