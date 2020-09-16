package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.GroupImage;
import com.persoff68.fatodo.service.GroupImageService;
import com.persoff68.fatodo.web.rest.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StoreController.ENDPOINT)
@RequiredArgsConstructor
public class StoreController {
    static final String ENDPOINT = "/api/store";
    private static final String THUMBNAIL_PREFIX = "thumbnail-";

    private final GroupImageService groupImageService;

    @GetMapping(value = "/group/{filename}")
    public ResponseEntity<ByteArrayResource> getGroupImage(@PathVariable String filename) {
        GroupImage image = groupImageService.getByFilename(filename);
        return ResponseUtils.createResponse(image.getContent(), image.getFilename());
    }

    @GetMapping(value = "/group/{filename}/thumbnail")
    public ResponseEntity<ByteArrayResource> getGroupImageThumbnail(@PathVariable String filename) {
        GroupImage image = groupImageService.getByFilename(filename);
        return ResponseUtils.createResponse(image.getThumbnail(), THUMBNAIL_PREFIX + image.getFilename());
    }

}
