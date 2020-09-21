package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.service.exception.ImageInvalidException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageUtils {
    private static final String FILE_EXTENSION = ".jpeg";

    private ImageUtils() {
    }

    public static String generateFilename(String prefix) {
        return prefix + UUID.randomUUID() + FILE_EXTENSION;
    }

    public static BufferedImage getBufferedImage(byte[] bytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new ImageInvalidException("Image not valid");
        }
    }

}
