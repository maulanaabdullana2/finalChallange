package com.codeid.eshopay_backend.service;

import com.codeid.eshopay_backend.model.dto.AuthResponseDto;
import com.codeid.eshopay_backend.model.dto.UserDto;
import com.codeid.eshopay_backend.model.dto.userResponseDto;

public interface UserService extends BaseCrudService<UserDto,Long> {
    AuthResponseDto login(UserDto userDto);
    //regsiter
    userResponseDto register(UserDto userDto);
}