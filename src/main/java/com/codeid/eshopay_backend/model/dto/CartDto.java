package com.codeid.eshopay_backend.model.dto;

import java.util.List;

import com.codeid.eshopay_backend.model.entity.CartItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private Long cartId;
    private Long userId;
    private List<CartItemDto> cartItems;
}
