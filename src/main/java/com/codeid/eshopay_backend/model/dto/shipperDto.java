package com.codeid.eshopay_backend.model.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class shipperDto {
    
    public Long shipperId;

    
    @Size(max = 40,message = "Length Value must not exceeded than 40" )
    private String companyName;


    @Size(max = 24,message = "Length Value must not exceeded than 24" )
    private String phone;
}
