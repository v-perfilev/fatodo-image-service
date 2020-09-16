package com.persoff68.fatodo.service.validator;

import com.persoff68.fatodo.service.exception.ImageInvalidException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ImageValidator {

    private final BufferedImage image;
    private final String format;
    private final int size;

    public ImageValidator(InputStream inputStream) {
        try {
            ImageInputStream input = ImageIO.createImageInputStream(inputStream);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (readers.hasNext()) {
                throw new ImageInvalidException("No image");
            }
            ImageReader reader = readers.next();
            reader.setInput(input);
            image = reader.read(0);
            format = reader.getFormatName();
            size = inputStream.readAllBytes().length;
        } catch (IOException e) {
            throw new ImageInvalidException("Broken image");
        }
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
