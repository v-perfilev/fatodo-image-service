package com.persoff68.fatodo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageFile implements AbstractModel {

    private String filename;

    private MultipartFile content;

}
