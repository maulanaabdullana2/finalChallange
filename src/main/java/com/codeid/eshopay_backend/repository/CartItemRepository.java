package com.codeid.eshopay_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Cart;
import com.codeid.eshopay_backend.model.entity.CartItems;
import com.codeid.eshopay_backend.model.entity.Product;


@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    @Query("SELECT ci FROM CartItems ci WHERE ci.cart = :cart AND ci.product = :product")
    Optional<CartItems> findByCartAndProduct(@Param("cart") Cart cart, @Param("product") Product product);

    @Query("SELECT ci FROM CartItems ci WHERE ci.cartItemId = :cartItemId AND ci.cart.user.userId = :userId")
    Optional<CartItems> findByCartItemIdAndUserId(@Param("cartItemId") Long cartItemId, @Param("userId") Long userId);

    @Query("SELECT ci FROM CartItems ci WHERE ci.cart = :cart")
    List<CartItems> findByCart(Cart cart);

    List<CartItems> findByCartUserUserId(Long userId);
}
