package com.codeid.eshopay_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Cart;


@Repository
public interface CartRepsoitory extends JpaRepository<Cart,Long> {
    @EntityGraph(attributePaths = "cartItems")
    Optional<Cart> findByUser_UserId(Long userId);
} 