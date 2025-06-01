package com.codeid.eshopay_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Cart;
import com.codeid.eshopay_backend.model.entity.CartItems;
import com.codeid.eshopay_backend.model.entity.CartItemsId;
import com.codeid.eshopay_backend.model.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, CartItemsId> {

    Optional<CartItems> findByCartAndProduct(Cart cart, Product product);

    List<CartItems> findByCartUserUserId(Long userId);

    
    List<CartItems> findByCartUserUserIdAndStatus(Long userId, String string);

    
    @Modifying
    @Query("DELETE FROM CartItems c WHERE c.cart.cartId = :cartId AND c.product.productId = :productId")
    void deleteByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
