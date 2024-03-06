package com.ll.topcastingbe.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserAndWriterNotMatchException extends RuntimeException {
    public UserAndWriterNotMatchException(String msg) {
        super(msg);
    }
}
