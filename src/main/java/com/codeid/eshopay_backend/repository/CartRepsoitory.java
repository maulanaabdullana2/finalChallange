package com.codeid.eshopay_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Cart;


@Repository
public interface CartRepsoitory extends JpaRepository<Cart,Long> {
    
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId);
    
} 