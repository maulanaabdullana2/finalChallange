package com.codeid.eshopay_backend.service;

import java.util.List;

import com.codeid.eshopay_backend.model.dto.CartItemDto;

public interface CartItemsService extends BaseCrudService<CartItemDto,Long> {
    
    CartItemDto addCartItem(Long userId, Long productId, long quantity);
    List<CartItemDto> getAllCartItemsByUser(Long userId);
    CartItemDto getCartByIdAndUserId(Long cartItemId, Long userId);
    
    void deleteCartItemsByUser(Long id, Long userId);
} 
