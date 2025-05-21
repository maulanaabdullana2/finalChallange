package com.codeid.eshopay_backend.service;

import com.codeid.eshopay_backend.model.dto.OrderDto;

public interface OrderService extends BaseCrudService<OrderDto,Long>{
    OrderDto createOrder (OrderDto orderDto, Long userId);  
    //create order berdasarkan userid
} 