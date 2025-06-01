package com.codeid.eshopay_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.CartDto;
import com.codeid.eshopay_backend.model.dto.CartItemDto;
import com.codeid.eshopay_backend.model.enumeration.EnumStatus;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.security.UserPrincipal;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/cart/")
@RequiredArgsConstructor
public class CartItemsController extends BaseCrudController<CartDto, Long> {

    public final CartService cartService;
    private final HttpServletRequest request;

    @Override
    protected BaseCrudService<CartDto, Long> getService() {
        return cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestBody CartItemDto cartItemDto,
            Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();

            CartDto cart = cartService.addCartItem(
                    userId,
                    cartItemDto.getProductId(),
                    cartItemDto.getQuantity());

            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            ApiResponse<?> apiResponse = new ApiResponse<>(
                    EnumStatus.Failed.toString(),
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PostMapping("/select")
    public ResponseEntity<?> selectCartItem(@RequestBody CartItemDto cartItemDto,
            Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();
            Long productId = cartItemDto.getProductId();
            boolean selected = cartItemDto.isSelected();

            cartService.selectCartItem(userId, productId, selected);

            ApiResponse<?> response = new ApiResponse<>(
                    EnumStatus.Succees.toString(),
                    "Cart item selection updated",
                    null);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponse<?> response = new ApiResponse<>(
                    EnumStatus.Failed.toString(),
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long productId,
            Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();

            cartService.deleteByCartUserUserIdAndProductProductId(userId, productId);

            ApiResponse<?> apiResponse = new ApiResponse<>(
                    EnumStatus.Succees.toString(),
                    "Cart item deleted successfully",
                    null);
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            ApiResponse<?> apiResponse = new ApiResponse<>(
                    EnumStatus.Failed.toString(),
                    e.getMessage(),
                    null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUserCart(Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();

            List<CartItemDto> cartItems = cartService.getCartByUser(userId);

            ApiResponse<List<CartItemDto>> response = new ApiResponse<>(
                    EnumStatus.Succees.toString(),
                    "Cart retrieved successfully",
                    cartItems);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<?> response = new ApiResponse<>(
                    EnumStatus.Failed.toString(),
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
