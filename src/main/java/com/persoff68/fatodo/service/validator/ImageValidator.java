package com.persoff68.fatodo.service.validator;

import com.persoff68.fatodo.service.exception.ImageInvalidException;
import com.persoff68.fatodo.service.util.ImageUtils;
import org.apache.tika.Tika;

import java.awt.image.BufferedImage;

public class ImageValidator {

    private static final Tika tika = new Tika();

    private final BufferedImage image;
    private final String format;
    private final int size;

    private ImageValidator(BufferedImage image, byte[] bytes) {
        this.image = image;
        this.format = tika.detect(bytes);
        this.size = bytes.length;
    }

    private void validateSize(int minSize, int maxSize) {
        if (size < minSize || size > maxSize) {
            throw new ImageInvalidException("Image size validation failed");
        }
    }

    private void validateDimensions(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width < minWidth || width > maxWidth || height < minHeight || height > maxHeight) {
            throw new ImageInvalidException("Image dimensions validation failed");
        }
    }

    private void validateRatio(int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();
        float ratio = (float) width / height;
        float requiredRatio = (float) x / y;
        float deviation = Math.abs(ratio - requiredRatio) / ratio;
        if (deviation > 0.01F) {
            throw new ImageInvalidException("Image ratio validation failed");
        }
    }

    private void validateExtension(String requiredFormat) {
        if (!format.equalsIgnoreCase(requiredFormat)) {
            throw new ImageInvalidException("Image extension validation failed");
        }
    }

    public static void validateGroupImage(byte[] bytes) {
        BufferedImage bufferedImage = ImageUtils.getBufferedImage(bytes);
        ImageValidator validator = new ImageValidator(bufferedImage, bytes);
        validator.validateExtension("image/jpeg");
        validator.validateDimensions(100, 500, 100, 500);
        validator.validateRatio(1, 1);
        validator.validateSize(1024, 1024 * 512);
    }

}
