package com.ll.topcastingbe.domain.member.controller;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SocialMemberController {
    @GetMapping("/socialLogin/{providerTypeCode}")
    public String socialLogin(String redirectUrl, @PathVariable String providerTypeCode) {
        if (redirectUrl.startsWith("http://localhost:3000")) {
            Cookie cookie = new Cookie("redirectUrlAfterSocialLogin", redirectUrl);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 10);
        }

        return "redirect:/oauth2/authorization/" + providerTypeCode;
    }


}
