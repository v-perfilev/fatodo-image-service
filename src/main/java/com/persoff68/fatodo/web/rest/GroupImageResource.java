package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.Image;
import com.persoff68.fatodo.model.dto.ImageDTO;
import com.persoff68.fatodo.model.mapper.ImageMapper;
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
    private final ImageMapper imageMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody ImageDTO imageDTO) {
        Image image = imageMapper.imageDTOToImage(imageDTO);
        String filename = groupImageService.create(image);
        return ResponseEntity.ok(filename);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> update(@Valid @RequestBody ImageDTO imageDTO) {
        Image image = imageMapper.imageDTOToImage(imageDTO);
        String filename = groupImageService.update(image);
        return ResponseEntity.ok(filename);
    }

    @DeleteMapping(value = "/{filename}")
    public ResponseEntity<Void> delete(@PathVariable String filename) {
        groupImageService.delete(filename);
        return ResponseEntity.ok().build();
    }

}
