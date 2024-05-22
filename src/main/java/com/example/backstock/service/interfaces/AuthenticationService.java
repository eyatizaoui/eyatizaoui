package com.example.backstock.service.interfaces;

import com.example.backstock.dto.AuthResponseDto;
import com.example.backstock.dto.LoginDto;
import com.example.backstock.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {


    String register(UserEntity user, String roleName);

    AuthResponseDto login(LoginDto loginDto);

    AuthResponseDto refreshToken(HttpServletRequest request);

    String logout();
}
