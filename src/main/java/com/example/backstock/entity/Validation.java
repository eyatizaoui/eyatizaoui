package com.example.backstock.entity;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Validation {

    public boolean emailValidation(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return StringUtils.hasText(email) && email.matches(emailRegex);

    }

    public boolean passwordValidation(String password){
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return StringUtils.hasText(password) && password.matches(passwordRegex);
    }

    public boolean usernameValidation(String username){
        String usernameRegex = "^[A-Za-z][A-Za-z0-9_]{7,29}$";
        return StringUtils.hasText(username) && username.matches(usernameRegex);
    }
}