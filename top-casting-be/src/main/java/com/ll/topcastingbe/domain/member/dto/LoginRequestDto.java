package com.ll.topcastingbe.domain.member.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
