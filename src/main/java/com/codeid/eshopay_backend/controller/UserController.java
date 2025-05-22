package com.codeid.eshopay_backend.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.AuthResponseDto;
import com.codeid.eshopay_backend.model.dto.UserDto;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

 @PostMapping("/login")
public ResponseEntity<ApiResponse<AuthResponseDto>> login(@RequestBody UserDto userDto) {
    try {
        AuthResponseDto authResponse = userService.login(userDto);
        ApiResponse<AuthResponseDto> successResponse = new ApiResponse<>(
                "success",
                "Login successful",
                authResponse
        );
        return ResponseEntity.ok(successResponse);
    } catch (RuntimeException e) {
        ApiResponse<AuthResponseDto> errorResponse = new ApiResponse<>(
                "error",
                e.getMessage(),
                null
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

//register
@PostMapping("/register")
public ResponseEntity Register (@RequestBody UserDto userDto) {
    try {
         UserDto response = userService.register(userDto);
        ApiResponse<UserDto> successResponse = new ApiResponse<>(
            "success",
            "Registration successful",
            response
        );
        return ResponseEntity.ok(successResponse);
    } catch (Exception e) {
        ApiResponse<UserDto> errorResponse = new ApiResponse<>(
            "error",
            e.getMessage(),
            null
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

}



}
