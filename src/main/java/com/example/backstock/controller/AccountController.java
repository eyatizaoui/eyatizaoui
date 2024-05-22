package com.example.backstock.controller;

import com.example.backstock.dto.ChangePasswordDto;
import com.example.backstock.dto.ResetPasswordDto;
import com.example.backstock.dto.UpdateProfileDto;
import com.example.backstock.entity.UserEntity;
import com.example.backstock.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/forgot-password")
    private String forgotPassword(@RequestParam(value = "emailAddress") String emailAddress) {
        return this.accountService.forgetPassword(emailAddress);
    }

    @PostMapping("/reset-password")
    private String resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return this.accountService.resetPassword(resetPasswordDto);
    }

    @GetMapping("/list-user-accounts")
    private List<UserEntity> listUserAccounts() {
        return this.accountService.listUserAccounts();
    }

    @PostMapping("/enable-account")
    private String enableAccount(@RequestParam(value = "userId") Long userId) {
        return this.accountService.enableAccount(userId);
    }

    @PostMapping("/disable-account")
    private String disableAccount(@RequestParam(value = "userId") Long userId) {
        return this.accountService.disableAccount(userId);
    }

    @GetMapping("/profile")
    public UserEntity getProfile(Principal principal) {
        return this.accountService.getProfile(principal.getName());
    }

    @PostMapping("change-password")
    public String changePassword(Principal principal, @RequestBody ChangePasswordDto changePasswordDto) {
        return this.accountService.changePassword(principal.getName(), changePasswordDto);
    }

    @PostMapping("update-profile")
    public String updateProfile(Principal principal, @RequestBody UpdateProfileDto updateProfileDto) {
        return this.accountService.updateProfile(principal.getName(), updateProfileDto);
    }
}
