package com.example.backstock.service.interfaces;

import com.example.backstock.dto.ChangePasswordDto;
import com.example.backstock.dto.ResetPasswordDto;
import com.example.backstock.dto.UpdateProfileDto;
import com.example.backstock.entity.UserEntity;

import java.util.List;

public interface AccountService {

    String forgetPassword(String emailAddress);

    String resetPassword(ResetPasswordDto resetPasswordDto);

    List<UserEntity> listUserAccounts();

    String enableAccount(Long userId);

    String disableAccount(Long userId);

    UserEntity getProfile(String userName);

    String updateProfile(String userName, UpdateProfileDto updateProfileDto);

    String changePassword(String userName, ChangePasswordDto changePasswordDto);
}


