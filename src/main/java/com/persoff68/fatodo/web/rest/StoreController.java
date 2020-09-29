package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Image;
import com.persoff68.fatodo.service.StoreService;
import com.persoff68.fatodo.web.rest.factory.StoreFactory;
import com.persoff68.fatodo.web.rest.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
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

    private final StoreFactory storeFactory;

    @GetMapping(value = "/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        StoreService storeService = storeFactory.getServiceForFilename(filename);
        Image image = storeService.getByFilename(filename);
        return ResponseUtils.createResponse(image.getContent(), image.getFilename(), false);
    }

    @GetMapping(value = "/{filename}/thumbnail")
    public ResponseEntity<Resource> getImageThumbnail(@PathVariable String filename) {
        StoreService storeService = storeFactory.getServiceForFilename(filename);
        Image image = storeService.getByFilename(filename);
        return ResponseUtils.createResponse(image.getThumbnail(), image.getFilename(), true);
    }

}
