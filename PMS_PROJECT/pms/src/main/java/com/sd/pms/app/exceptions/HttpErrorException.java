package com.sd.pms.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class HttpErrorException extends Exception{
    public HttpErrorException(String message) {
        super(message);
    }
}
