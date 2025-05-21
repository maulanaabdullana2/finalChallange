package com.codeid.eshopay_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeid.eshopay_backend.model.dto.OrderDto;
import com.codeid.eshopay_backend.model.response.ApiResponse;
import com.codeid.eshopay_backend.security.UserPrincipal;
import com.codeid.eshopay_backend.service.BaseCrudService;
import com.codeid.eshopay_backend.service.OrderService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController extends BaseCrudController<OrderDto, Long> {
    private final OrderService OrderService;

    @Override
    protected BaseCrudService<OrderDto, Long> getService() {
        return OrderService;
    }

   @PostMapping("/add")
public ResponseEntity<?> saveEntity(@RequestBody OrderDto projectDto, Authentication authentication) {
    try {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userId = userPrincipal.getId();

        OrderDto order = OrderService.createOrder(projectDto, userId);

        ApiResponse<OrderDto> response = new ApiResponse<>(
            "success",
            "Create Order successfully",
            order
        );
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        ApiResponse<String> errorResponse = new ApiResponse<>(
            "error",
            "Failed to create order: " + e.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}


}
