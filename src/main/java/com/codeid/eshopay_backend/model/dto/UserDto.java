package com.codeid.eshopay_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private Long id;
    private String userEmail;
    private String userPassword;
    //confirmpassword
    private String ConfirmPassword;
    private Long userPhone;
}
