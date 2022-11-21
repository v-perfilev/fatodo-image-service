package com.persoff68.fatodo.web.rest.util;

import org.bson.types.Binary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.TimeUnit;

public class ResponseUtils {
    private static final String THUMBNAIL_PREFIX = "thumbnail-";

    private ResponseUtils() {
    }

    public static ResponseEntity<Resource> createResponse(Binary image, String filename, boolean isThumbnail) {
        byte[] bytes = image.getData();
        ByteArrayResource resource = new ByteArrayResource(bytes);
        filename = isThumbnail ? THUMBNAIL_PREFIX + filename : filename;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .body(resource);
    }

}
