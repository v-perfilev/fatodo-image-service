package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.service.exception.ImageInvalidException;
import com.persoff68.fatodo.service.validator.ImageValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static BufferedImage getBufferedImage(InputStream inputStream) {
        try {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new ImageInvalidException(e.getMessage());
        }
    }

    public static InputStream getInputStreamFromFile(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new ImageInvalidException(e.getMessage());
        }
    }

    public static void validateGroupImage(InputStream inputStream) {
        ImageValidator validator = new ImageValidator(inputStream);
        validator.validateExtension("jpg");
        validator.validateDimensions(100, 500, 100, 500);
        validator.validateRatio(1, 1);
        validator.validateSize(1024, 1024 * 512);
    }

}
