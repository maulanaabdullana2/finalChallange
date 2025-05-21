package com.codeid.eshopay_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {
    private String userName;
    private String userEmail;
    private String userPassword;
    private Long userPhone;
}
