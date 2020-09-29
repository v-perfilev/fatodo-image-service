package com.persoff68.fatodo;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

public class TestImageUtils {
    private static final String bigSquareJpgPath = "images/jpg-big-square.jpg";
    private static final String mediumSquareJpgPath = "images/jpg-medium-square.jpg";
    private static final String smallSquareJpgPath = "images/jpg-small-square.jpg";
    private static final String mediumRectangularJpgPath = "images/jpg-medium-rectangular.jpg";
    private static final String mediumSquarePngPath = "images/png-medium-square.png";

    public static byte[] loadBigSquareJpg() {
        return loadImageAsByteArray(bigSquareJpgPath);
    }

    public static byte[] loadMediumSquareJpg() {
        return loadImageAsByteArray(mediumSquareJpgPath);
    }

    public static byte[] loadSmallSquareJpg() {
        return loadImageAsByteArray(smallSquareJpgPath);
    }

    public static byte[] loadMediumRectangularJpg() {
        return loadImageAsByteArray(mediumRectangularJpgPath);
    }

    public static byte[] loadMediumSquarePng() {
        return loadImageAsByteArray(mediumSquarePngPath);
    }

    private static byte[] loadImageAsByteArray(String path) {
        try {
            return new ClassPathResource(path).getInputStream().readAllBytes();
        } catch (IOException e) {
            throw  new RuntimeException("Test image file not found");
        }
    }

}
