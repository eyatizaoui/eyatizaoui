package com.example.backstock.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String newPassword;
    private String confirmation;
}
