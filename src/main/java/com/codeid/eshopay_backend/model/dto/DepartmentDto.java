package com.codeid.eshopay_backend.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private Long departmentId;

    @Size(max = 30, message = "Length value must not exceeded than 30")
    private String departmentName;
}