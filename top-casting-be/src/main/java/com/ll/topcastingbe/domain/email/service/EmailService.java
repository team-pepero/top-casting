package com.ll.topcastingbe.domain.email.service;

public interface EmailService {
    void sendMail(String setFrom, String toMail, String title, String content);
}
