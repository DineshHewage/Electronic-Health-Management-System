package com.sd.pms.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FieldValidationException extends Exception{
    public FieldValidationException(String message) {
        super(message);
    }
}