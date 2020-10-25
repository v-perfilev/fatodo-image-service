package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "ftd_group_image")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupImage extends Image {

    public GroupImage(String filename, Binary content, Binary thumbnail) {
        super(filename, content, thumbnail);
    }

    public GroupImage(Image image) {
        super(image.getFilename(), image.getContent(), image.getThumbnail());
        this.setId(image.getId());
    }

}
