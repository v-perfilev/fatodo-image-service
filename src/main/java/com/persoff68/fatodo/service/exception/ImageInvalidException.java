package com.persoff68.fatodo.service.exception;

import com.persoff68.fatodo.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ImageInvalidException extends AbstractException {
    private static final String FEEDBACK_CODE = "image.invalid";

    public ImageInvalidException(String message) {
        super(HttpStatus.BAD_REQUEST, message, FEEDBACK_CODE);
    }
}
