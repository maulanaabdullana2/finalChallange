package com.codeid.eshopay_backend.service;

import java.util.List;

import com.codeid.eshopay_backend.model.dto.CartItemDto;
import com.codeid.eshopay_backend.model.dto.CartItemDtoResponse;

public interface CartItemsService extends BaseCrudService<CartItemDto,Long> {
    
    CartItemDtoResponse addCartItem(Long userId, Long productId, long quantity);
    List<CartItemDtoResponse> getAllCartItemsByUser(Long userId);
    CartItemDtoResponse getCartByIdAndUserId(Long cartItemId, Long userId);
    
    void deleteCartItemsByUser(Long id, Long userId);
} 
