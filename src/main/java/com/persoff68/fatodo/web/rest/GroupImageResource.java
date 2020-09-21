package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.dto.ImageDTO;
import com.persoff68.fatodo.service.GroupImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
@RequestMapping(GroupImageResource.ENDPOINT)
@RequiredArgsConstructor
public class GroupImageResource {
    static final String ENDPOINT = "/api/group-images";

    private final GroupImageService groupImageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody @Valid ImageDTO imageDTO) {
        String name = groupImageService.create(imageDTO.getContent());
        return ResponseEntity.ok(name);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody @Valid ImageDTO imageDTO) {
        String name = groupImageService.update(imageDTO.getFilename(), imageDTO.getContent());
        return ResponseEntity.ok(name);
    }

    @DeleteMapping(value = "/{filename}")
    public ResponseEntity<Void> delete(@PathVariable String filename) {
        groupImageService.delete(filename);
        return ResponseEntity.ok().build();
    }

}
