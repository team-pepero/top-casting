package com.ll.topcastingbe.global.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("custom.jwt")
public class JwtProps {

    private String secretKey;
}
