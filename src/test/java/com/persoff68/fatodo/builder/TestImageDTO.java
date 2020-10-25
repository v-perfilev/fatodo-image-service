package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.dto.ImageDTO;
import lombok.Builder;

import javax.validation.constraints.NotNull;

public class TestImageDTO extends ImageDTO {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    TestImageDTO(String filename, @NotNull byte[] content) {
        super(filename, content);
    }

    public static TestImageDTOBuilder defaultBuilder() {
        return TestImageDTO.builder()
                .filename(DEFAULT_VALUE)
                .content(DEFAULT_VALUE.getBytes());
    }

}
