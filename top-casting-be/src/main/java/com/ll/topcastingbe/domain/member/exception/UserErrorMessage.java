package com.ll.topcastingbe.domain.member.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserErrorMessage {

    USER_AND_WRITER_NOT_MATCH("수정 권한이 없습니다."),
    USER_NOT_FOUND("해당 사용자를 찾을 수 없습니다.");

    private final String message;
}
