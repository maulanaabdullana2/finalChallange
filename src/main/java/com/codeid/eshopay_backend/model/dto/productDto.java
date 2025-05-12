package com.codeid.eshopay_backend.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class productDto {
    private Long productId;
    
    @Size(max = 255, message = "Product name must be less than 255 characters")
    private String productName;

    private supplierDto supplier;

    private categoryDto category;

    @Size(max = 20,message = "Product code must be less than 20 characters")
    private String quantityPerUnit;

    private Double unitPrice;

    private Long unitsInStock;

    private Long unitsOnOrder;

    private Long reorderLevel;

    private Boolean discontinued;
    
    private String photo;
}
