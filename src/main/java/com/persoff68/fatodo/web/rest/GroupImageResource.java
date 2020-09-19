package com.persoff68.fatodo.web.rest;

import com.persoff68.fatodo.model.ImageFile;
import com.persoff68.fatodo.model.dto.ImageFileDTO;
import com.persoff68.fatodo.model.mapper.ImageMapper;
import com.persoff68.fatodo.service.GroupImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(GroupImageResource.ENDPOINT)
@RequiredArgsConstructor
public class GroupImageResource {
    static final String ENDPOINT = "/api/group-images";

    private final GroupImageService groupImageService;
    private final ImageMapper imageMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> create(@ModelAttribute ImageFileDTO imageFileDTO) {
        ImageFile imageFile = imageMapper.imageDTOToImage(imageFileDTO);
        String name = groupImageService.create(imageFile);
        return ResponseEntity.ok(name);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> update(@ModelAttribute ImageFileDTO imageFileDTO) {
        ImageFile imageFile = imageMapper.imageDTOToImage(imageFileDTO);
        String name = groupImageService.update(imageFile);
        return ResponseEntity.ok(name);
    }

    @DeleteMapping(value = "/{filename}")
    public ResponseEntity<Void> delete(@PathVariable String filename) {
        groupImageService.delete(filename);
        return ResponseEntity.ok().build();
    }

}
