package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Image extends AbstractAuditingModel {

    @NotNull
    @Indexed(unique = true)
    protected String filename;

    @NotNull
    protected Binary content;
    @NotNull
    protected Binary thumbnail;

    public Image(String filename, Binary content, Binary thumbnail) {
        this.filename = filename;
        this.content = content;
        this.thumbnail = thumbnail;
    }

}
