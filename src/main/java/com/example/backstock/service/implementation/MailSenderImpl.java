package com.example.backstock.service.implementation;

import com.example.backstock.service.interfaces.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderImpl implements MailSender {
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void send(SimpleMailMessage mail) {
        javaMailSender.send(mail);
    }
}
