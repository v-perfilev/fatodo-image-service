package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "ftd_group_image")
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupImage extends ImageWithThumbnail {

    public GroupImage(String url, byte[] content, byte[] thumbnail) {
        super(url, content, thumbnail);
    }

}
