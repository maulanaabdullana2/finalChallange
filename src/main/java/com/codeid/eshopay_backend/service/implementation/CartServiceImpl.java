package com.codeid.eshopay_backend.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.CartDto;
import com.codeid.eshopay_backend.model.dto.CartItemDto;
import com.codeid.eshopay_backend.model.dto.productDto;
import com.codeid.eshopay_backend.model.entity.Cart;
import com.codeid.eshopay_backend.model.entity.CartItems;
import com.codeid.eshopay_backend.model.entity.CartItemsId;
import com.codeid.eshopay_backend.model.entity.OrderDetail;
import com.codeid.eshopay_backend.model.entity.Product;
import com.codeid.eshopay_backend.model.entity.User;
import com.codeid.eshopay_backend.repository.CartItemRepository;
import com.codeid.eshopay_backend.repository.CartRepsoitory;
import com.codeid.eshopay_backend.service.CartService;
import com.codeid.eshopay_backend.repository.productRepository;
import com.codeid.eshopay_backend.repository.userRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepsoitory cartRepository;
    private final productRepository productRepository;
    private final CartItemRepository CartItemRepository;

    public static CartDto mapDto(Cart cart) {
        return CartDto.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .cartItems(cart.getCartItems() != null
                        ? cart.getCartItems().stream()
                                .map(CartServiceImpl::mapCartItemDto)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    public static Cart mapToEntity(CartDto cartDto) {
        return Cart.builder()
                .cartId(cartDto.getCartId())
                .user(User.builder().userId(cartDto.getUserId()).build())
                .build();
    }

    public static CartItemDto mapCartItemDto(CartItems cartItem) {
        return CartItemDto.builder()
                .productId(cartItem.getProduct().getProductId())
                .quantity(cartItem.getQuantity())
                .product(cartItem.getProduct() != null
                        ? productServiceImpl.mapToDto(cartItem.getProduct())
                        : null)
                .build();
    }

    @Override
    public List<CartDto> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public CartDto findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public CartDto save(CartDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public CartDto update(Long id, CartDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    @Transactional
    public CartDto addCartItem(Long userId, Long productId, long quantity) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for userId: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found for productId: " + productId));

        Optional<CartItems> optionalCartItem = CartItemRepository.findByCartAndProduct(cart, product);

        if (optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItems cartItem = new CartItems();
            CartItemsId id = new CartItemsId(cart.getCartId(), product.getProductId());
            cartItem.setId(id);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getUnitPrice());
            cartItem.setDiscount(0.0);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);

        Cart updatedCart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found after update"));

        return mapDto(updatedCart);
    }

    public void selectCartItem(Long userId, Long productId, boolean selected) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItems cartItem = CartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setStatus(selected ? "SELECTED" : "ACTIVE");
        CartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void deleteByCartUserUserIdAndProductProductId(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItems cartItem = CartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Item not found in your cart"));

        CartItemRepository.deleteByCartIdAndProductId(cartItem.getCart().getCartId(), product.getProductId());
    }

    @Override
    public List<CartItemDto> getCartByUser(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return cart.getCartItems().stream()
                .map(CartServiceImpl::mapCartItemDto)
                .collect(Collectors.toList());
    }


}
