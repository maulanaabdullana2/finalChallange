package com.codeid.eshopay_backend.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.codeid.eshopay_backend.model.enumeration.EnumTypeBank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long orderId;
    private String orderNo;
    private LocalDateTime orderDate;
    private LocalDateTime requiredDate;
    private LocalDateTime shippedDate;
    private Double freight;
    private String shipName;
    private Double totalDiscount;
    private Double totalAmount;
    private EnumTypeBank paymentType;
    private String cardNo;
    private String transacNo;
    private LocalDateTime transacDate;
    private String refNo;
    private Long locationId;
    private Long userId;
    private String bankCode;

    private List<OrderDetailDto> orderDetails;
    
    
}
