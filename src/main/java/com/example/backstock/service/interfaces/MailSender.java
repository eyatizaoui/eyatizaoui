package com.example.backstock.service.interfaces;

import org.springframework.mail.SimpleMailMessage;

public interface MailSender {
    void send(SimpleMailMessage mail);
}
