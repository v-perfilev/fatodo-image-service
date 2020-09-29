package com.persoff68.fatodo;

import com.persoff68.fatodo.model.dto.ImageDTO;

public class FactoryUtils {

    public static ImageDTO createImageDTO(String filename, byte[] content) {
        return new ImageDTO(filename, content);
    }

}
