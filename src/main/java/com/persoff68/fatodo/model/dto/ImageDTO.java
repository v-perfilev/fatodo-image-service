package com.persoff68.fatodo.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class ImageDTO implements AbstractDTO {

    private String filename;

    @NotNull
    private MultipartFile content;

}
