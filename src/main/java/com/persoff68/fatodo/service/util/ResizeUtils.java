package com.persoff68.fatodo.service.util;

import com.persoff68.fatodo.service.exception.ImageInvalidException;
import org.bson.types.Binary;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResizeUtils {
    private static final int USUAL_WIDTH = 1000;
    private static final int THUMBNAIL_WIDTH = 100;

    private ResizeUtils() {
    }

    public static Binary getOriginal(BufferedImage bufferedImage) {
        byte[] bytes = getResizedInByte(bufferedImage, USUAL_WIDTH);
        return new Binary(bytes);
    }

    public static Binary getThumbnail(BufferedImage bufferedImage) {
        byte[] bytes = getResizedInByte(bufferedImage, THUMBNAIL_WIDTH);
        return new Binary(bytes);
    }

    private static byte[] getResizedInByte(BufferedImage bufferedImage, int newWidth) {
        BufferedImage outputImage = Scalr.resize(bufferedImage, newWidth);
        return convertBufferedImageToByteArray(outputImage);
    }

    private static byte[] convertBufferedImageToByteArray(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpeg", outputStream);
            outputStream.flush();
            byte[] imageInByte = outputStream.toByteArray();
            outputStream.close();
            return imageInByte;
        } catch (IOException e) {
            throw new ImageInvalidException("Broken image");
        }
    }

}
