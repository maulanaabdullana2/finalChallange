package com.codeid.eshopay_backend.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class supplierDto {

    public Long supplierId;

    @Size(max = 40,message = "Length Value must not exceeded than 40" )
    public String companyName;
}
