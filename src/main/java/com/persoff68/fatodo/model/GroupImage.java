package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "ftd_group_image")
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupImage extends Image {

    public GroupImage(String filename, Binary content, Binary thumbnail) {
        super(filename, content, thumbnail);
    }

}
