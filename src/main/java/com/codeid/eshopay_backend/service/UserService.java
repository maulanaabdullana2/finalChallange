package com.codeid.eshopay_backend.service;

import com.codeid.eshopay_backend.model.dto.AuthResponseDto;
import com.codeid.eshopay_backend.model.dto.UserDto;

public interface UserService extends BaseCrudService<UserDto,Long> {
    AuthResponseDto login(UserDto userDto);
    //regsiter
    UserDto register(UserDto userDto);
}