package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class Image implements AbstractModel {

    private String filename;

    private MultipartFile content;

}
