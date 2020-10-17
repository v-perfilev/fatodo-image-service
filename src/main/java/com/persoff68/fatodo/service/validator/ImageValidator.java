package com.persoff68.fatodo.service.validator;

import com.persoff68.fatodo.service.exception.ImageInvalidException;
import org.apache.tika.Tika;

import java.awt.image.BufferedImage;

public class ImageValidator {

    private final BufferedImage image;
    private final String format;
    private final int size;

    public ImageValidator(BufferedImage image, byte[] bytes) {
        this.image = image;
        this.format = new Tika().detect(bytes);
        this.size = bytes.length;
    }

    public void validateSize(int minSize, int maxSize) {
        if (size < minSize || size > maxSize) {
            throw new ImageInvalidException("Image size validation failed");
        }
    }

    public void validateDimensions(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width < minWidth || width > maxWidth || height < minHeight || height > maxHeight) {
            throw new ImageInvalidException("Image dimensions validation failed");
        }
    }

    public void validateRatio(int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();
        float ratio = (float) width / height;
        float requiredRatio = (float) x / y;
        float deviation = Math.abs(ratio - requiredRatio) / ratio;
        if (deviation > 0.01F) {
            throw new ImageInvalidException("Image ratio validation failed");
        }
    }

    public void validateExtension(String requiredFormat) {
        if (!format.equalsIgnoreCase(requiredFormat)) {
            throw new ImageInvalidException("Image extension validation failed");
        }
    }

}
