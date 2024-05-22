package com.example.backstock.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String confirmationKeyValue;
    private String newPassword;
    private String confirmedNewPassword;
}