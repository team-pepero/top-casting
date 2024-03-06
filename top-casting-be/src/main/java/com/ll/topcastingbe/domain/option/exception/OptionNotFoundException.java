package com.ll.topcastingbe.domain.option.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OptionNotFoundException  extends RuntimeException {
    public OptionNotFoundException(String message) {
        super(message);
    }
}
