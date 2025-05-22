package com.codeid.eshopay_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Long productId;
    private Long quantity;
    private productDto product;
    private Long cartItemId;
}
