package com.persoff68.fatodo.web.rest.util;

import org.bson.types.Binary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {
    private static final String THUMBNAIL_PREFIX = "thumbnail-";

    public static ResponseEntity<ByteArrayResource> createResponse(Binary image, String filename, boolean isThumbnail) {
        byte[] data = image.getData();
        ByteArrayResource resource = new ByteArrayResource(data);
        filename = isThumbnail ? THUMBNAIL_PREFIX + filename : filename;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentLength(data.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
