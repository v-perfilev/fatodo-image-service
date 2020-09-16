package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.service.exception.ImageInvalidException;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResizeUtils {
    private static final int THUMBNAIL_WIDTH = 50;

    public static byte[] getOriginal(BufferedImage bufferedImage) {
        return getOriginalInByte(bufferedImage);
    }

    public static byte[] getThumbnail(BufferedImage bufferedImage) {
        return getResizedInByte(bufferedImage, THUMBNAIL_WIDTH);
    }

    private static byte[] getOriginalInByte(BufferedImage bufferedImage) {
        return convertBufferedImageToByteArray(bufferedImage);
    }

    private static byte[] getResizedInByte(BufferedImage bufferedImage, int newWidth) {
        BufferedImage outputImage = Scalr.resize(bufferedImage, newWidth);
        return convertBufferedImageToByteArray(outputImage);
    }

    private static byte[] convertBufferedImageToByteArray(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            byte[] imageInByte = outputStream.toByteArray();
            outputStream.close();
            return imageInByte;
        } catch (IOException e) {
            throw new ImageInvalidException("Broken image");
        }
    }

}
