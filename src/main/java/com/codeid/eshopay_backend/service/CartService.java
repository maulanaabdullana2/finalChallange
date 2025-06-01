package com.codeid.eshopay_backend.service;

import java.util.List;

import com.codeid.eshopay_backend.model.dto.CartDto;
import com.codeid.eshopay_backend.model.dto.CartItemDto;

public interface CartService extends BaseCrudService<CartDto,Long> {
    
    CartDto addCartItem(Long userId, Long productId, long quantity);
    void selectCartItem(Long userId, Long productId, boolean selected);
    void deleteByCartUserUserIdAndProductProductId(Long userId, Long productId);
    List<CartItemDto> getCartByUser(Long userId);
} 
