package com.persoff68.fatodo.service.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.exception.attribute.AttributeHandler;
import com.persoff68.fatodo.service.exception.CommonDatabaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor
public class ServiceExceptionHandling {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(Exception e, HttpServletRequest request) throws IOException {
        Exception exception = new CommonDatabaseException(e.getMessage());
        AttributeHandler attributeHandler = new AttributeHandler(request, exception);
        return attributeHandler.getResponseEntity(objectMapper);
    }

}
