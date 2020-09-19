package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.service.exception.ImageInvalidException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ImageUtils {
    private static final String FILE_EXTENSION = ".jpeg";

    public static String generateFilename(String prefix) {
        return prefix + UUID.randomUUID() + FILE_EXTENSION;
    }

    public static byte[] getBytes(MultipartFile content) {
        try {
            return content.getBytes();
        } catch (IOException e) {
            throw new ImageInvalidException(e.getMessage());
        }
    }

    public static BufferedImage getBufferedImage(MultipartFile content) {
        try {
            InputStream inputStream = content.getInputStream();
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new ImageInvalidException(e.getMessage());
        }
    }


}
