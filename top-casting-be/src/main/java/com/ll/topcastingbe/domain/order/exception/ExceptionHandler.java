package com.ll.topcastingbe.domain.order.exception;



import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponseDto> businessException(BusinessException e) {
        log.error(e.getMessage(), e);
        final ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.of(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponseDto);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> exception(Exception e) {
        log.error(e.getMessage(), e);
        final ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.of(ErrorMessage.INTERNAL_SERVER_ERROR.getMessage());
        return ResponseEntity.internalServerError().body(exceptionResponseDto);
    }

}
