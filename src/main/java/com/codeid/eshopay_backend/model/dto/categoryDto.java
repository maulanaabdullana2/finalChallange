package com.codeid.eshopay_backend.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class categoryDto {
    private Long categoryId;

    @Size(max = 15,message = "Length Value must not exceeded than 30")
    private String categoryName;

    private String categoryDescription;
}
