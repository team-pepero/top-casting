package com.ll.topcastingbe.global.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Builder
@AllArgsConstructor
@Data
public class ErrorResponseDto {
    private String code;
    private String message;
    private List<FieldError> errors;

    public static ErrorResponseDto of(final MethodArgumentTypeMismatchException e) {

        String field = e.getName();
        String value = Optional.ofNullable(e.getValue())
                .map(o -> o.toString())
                .orElse("");
        List<FieldError> errors = FieldError.of(field, value, e.getErrorCode());

        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(),e.getMessage(), errors);
    }

    @AllArgsConstructor
    private static class FieldError {
        private String field;
        private String value;
        private String message;

        private static List<FieldError> of(final String field, final String value, final String message) {
            List<FieldError> errors = new ArrayList<>();
            errors.add(new FieldError(field, value, message));
            return errors;
        }

    }

}



