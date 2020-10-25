package com.persoff68.fatodo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Image extends AbstractAuditingModel {

    @NotNull
    @Indexed(unique = true)
    protected String filename;

    @NotNull
    protected Binary content;
    @NotNull
    protected Binary thumbnail;

}
