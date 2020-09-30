package com.persoff68.fatodo.model.dto;

import com.persoff68.fatodo.config.constant.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO implements Serializable {
    protected static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    private String filename;

    @NotNull
    private byte[] content;

}