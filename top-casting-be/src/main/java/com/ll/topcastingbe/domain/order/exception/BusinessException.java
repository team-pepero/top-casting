package com.ll.topcastingbe.domain.order.exception;

public class BusinessException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public BusinessException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
