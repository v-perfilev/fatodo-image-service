package com.persoff68.fatodo.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageFileDTO implements AbstractDTO {

    private String filename;

    private MultipartFile content;

}
