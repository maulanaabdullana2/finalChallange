package com.codeid.eshopay_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDto {
    private Long orderId;
    private Long productId;
    private Double price;
    private Long quantity;
    private Double discount;
    private Double voucher;
}
