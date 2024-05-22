package com.example.backstock.controller;

import com.example.backstock.dto.AuthResponseDto;
import com.example.backstock.dto.LoginDto;
import com.example.backstock.entity.UserEntity;
import com.example.backstock.service.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    private String register(@RequestParam(value = "roleName") String roleName, @RequestBody UserEntity user) {
        return this.authenticationService.register(user, roleName);
    }

    @PostMapping("/login")
    private AuthResponseDto login(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/refresh")
    private AuthResponseDto refreshToken(HttpServletRequest request) {
        return this.authenticationService.refreshToken(request);
    }

    @PostMapping("/logout")
    private String logout() {
        return this.authenticationService.logout();
    }

}
