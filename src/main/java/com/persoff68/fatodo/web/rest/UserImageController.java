package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.dto.ImageDTO;
import com.persoff68.fatodo.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(UserImageController.ENDPOINT)
@RequiredArgsConstructor
public class UserImageController {
    static final String ENDPOINT = "/api/user-image";

    private final UserImageService userImageService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ImageDTO imageDTO) {
        String name = userImageService.create(imageDTO.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(name);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody @Valid ImageDTO imageDTO) {
        String name = userImageService.update(imageDTO.getFilename(), imageDTO.getContent());
        return ResponseEntity.ok(name);
    }

    @DeleteMapping(value = "/{filename}")
    public ResponseEntity<Void> delete(@PathVariable String filename) {
        userImageService.delete(filename);
        return ResponseEntity.ok().build();
    }

}
