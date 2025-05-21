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

import com.codeid.eshopay_backend.model.dto.CartItemDto;
import com.codeid.eshopay_backend.model.dto.CartItemDtoResponse;
import com.codeid.eshopay_backend.model.enumeration.EnumStatus;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.security.UserPrincipal;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.CartItemsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/cart/")
@RequiredArgsConstructor
public class CartItemsController extends BaseCrudController<CartItemDto, Long> {

    public final CartItemsService cartItemsService;
    private final HttpServletRequest request;

    @Override
    protected BaseCrudService<CartItemDto, Long> getService() {
        return cartItemsService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemDtoResponse> addCartItem(@RequestBody CartItemDto cartItemDto,
            Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();

            CartItemDtoResponse cart = cartItemsService.addCartItem(userId, cartItemDto.getProductId(),
                    cartItemDto.getQuantity());
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCartItemsByUserId(Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();
            List<CartItemDtoResponse> cartItems = cartItemsService.getAllCartItemsByUser(userId);
            ApiResponse apiResponse = new ApiResponse<>(
                    EnumStatus.Succees.toString(),
                    "Cart items retrieved successfully",
                    cartItems);
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            ApiResponse apiResponse = new ApiResponse<>(
                    EnumStatus.Failed.toString(),
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/by-cart/{id}")
    public ResponseEntity<?> getCartItemById(@PathVariable Long id, Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();

            CartItemDtoResponse cartItem = cartItemsService.getCartByIdAndUserId(id, userId);
            ApiResponse<CartItemDtoResponse> apiResponse = new ApiResponse<>(
                    EnumStatus.Succees.toString(),
                    "Cart item found",
                    cartItem);
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(
                    EnumStatus.Failed.toString(),
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    //delete
    @DeleteMapping("/by-cart/{id}")
    public ResponseEntity<?> deleteCartItemById(@PathVariable Long id, Authentication authentication) {
        try {
             UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();
            cartItemsService.deleteCartItemsByUser(id, userId);
            ApiResponse apiResponse = new ApiResponse<>(
                EnumStatus.Succees.toString(),
                "Cart item deleted successfully",
                null);
                return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<Object> apiResponse = new ApiResponse<>(
                EnumStatus.Failed.toString(),
                e.getMessage(),
                null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

}
