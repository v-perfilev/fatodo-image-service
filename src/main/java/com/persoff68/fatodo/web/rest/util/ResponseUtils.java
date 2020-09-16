package com.persoff68.fatodo.web.rest.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity<ByteArrayResource> createResponse(byte[] image, String filename) {
        ByteArrayResource resource = new ByteArrayResource(image);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentLength(image.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
