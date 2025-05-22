package com.codeid.eshopay_backend.service;

import java.util.List;

import com.codeid.eshopay_backend.model.dto.OrderDto;

public interface OrderService extends BaseCrudService<OrderDto,Long>{
    OrderDto createOrder (OrderDto orderDto, Long userId);  
    //Get order userid
    List<OrderDto> getOrderByUserId(Long userId);
    OrderDto getOrderByOrderId(Long orderId,Long userid);
} 