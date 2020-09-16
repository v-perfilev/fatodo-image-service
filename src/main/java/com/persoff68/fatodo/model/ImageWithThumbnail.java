package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ImageWithThumbnail extends AbstractAuditingModel {

    @NotNull
    @Indexed(unique = true)
    protected String filename;

    @NotNull
    protected byte[] content;
    @NotNull
    protected byte[] thumbnail;

    public ImageWithThumbnail(String filename, byte[] content, byte[] thumbnail) {
        super();
        this.filename = filename;
        this.content = content;
        this.thumbnail = thumbnail;
    }

}
