package com.codeid.eshopay_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE o.orderId = :orderId AND o.user.userId = :userId")
    Order findByOrderIdAndUserUserId(@Param("orderId") Long orderId, @Param("userId") Long userId);

}