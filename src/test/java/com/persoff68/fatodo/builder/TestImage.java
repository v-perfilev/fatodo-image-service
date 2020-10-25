package com.persoff68.fatodo.builder;

import com.persoff68.fatodo.model.Image;
import lombok.Builder;
import org.bson.types.Binary;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TestImage extends Image {
    private static final String DEFAULT_VALUE = "test_value";

    @Builder
    public TestImage(UUID id, @NotNull String filename, @NotNull Binary content, @NotNull Binary thumbnail) {
        super(filename, content, thumbnail);
        this.setId(id);
    }

    public static TestImageBuilder defaultBuilder() {
        return TestImage.builder()
                .id(UUID.randomUUID())
                .filename(DEFAULT_VALUE)
                .content(new Binary(new byte[]{}))
                .thumbnail(new Binary(new byte[]{}));
    }

}
