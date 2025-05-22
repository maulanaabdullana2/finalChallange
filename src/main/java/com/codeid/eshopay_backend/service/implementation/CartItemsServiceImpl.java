package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.CartItemDto;
import com.codeid.eshopay_backend.model.entity.Cart;
import com.codeid.eshopay_backend.model.entity.CartItems;
import com.codeid.eshopay_backend.model.entity.Product;
import com.codeid.eshopay_backend.repository.CartItemRepository;
import com.codeid.eshopay_backend.repository.CartRepsoitory;
import com.codeid.eshopay_backend.service.CartItemsService;
import com.codeid.eshopay_backend.repository.productRepository;
import com.codeid.eshopay_backend.repository.userRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemsServiceImpl implements CartItemsService {

    private final CartRepsoitory cartRepsoitory;
    private final CartItemRepository cartItemRepository;
    private final productRepository productRepository;
    private final userRepository userRepository;
public static CartItemDto mapDto(CartItems cartItems) {
    return CartItemDto.builder()
            .cartItemId(cartItems.getCartItemId())
            .productId(cartItems.getProduct() != null ? cartItems.getProduct().getProductId() : null) // <- Tambah ini
            .product(productServiceImpl.mapToDto(cartItems.getProduct()))
            .quantity(cartItems.getQuantity())
            .build();
}

    public static CartItems mapToEntity(CartItemDto cartItemDto) {
        return CartItems.builder()
                .cartItemId(cartItemDto.getCartItemId())
                .product(productServiceImpl.mapToEntity(cartItemDto.getProduct()))
                .quantity(cartItemDto.getQuantity())
                .build();
    }

    @Override
    public List<CartItemDto> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public CartItemDto findById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public CartItemDto save(CartItemDto entity) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public CartItemDto update(Long id, CartItemDto entity) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    @Transactional
    public CartItemDto addCartItem(Long userId, Long productId, long quantity) {
        Cart cart = cartRepsoitory.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + productId));

        Optional<CartItems> optionalCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return mapDto(cartItem);
        } else {
            if (quantity <= 0) {
                throw new RuntimeException("Quantity must be greater than zero");
            }
            CartItems cartItem = new CartItems();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getUnitPrice());
            return mapDto(cartItemRepository.save(cartItem));
        }
    }

    @Override
    public List<CartItemDto> getAllCartItemsByUser(Long userId) {
        Cart cart = cartRepsoitory.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for userId: " + userId));

        List<CartItems> cartItems = cartItemRepository.findByCart(cart);

        return cartItems.stream()
                .map(CartItemsServiceImpl::mapDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemDto getCartByIdAndUserId(Long cartItemId, Long userId) {
        return cartItemRepository.findByCartItemIdAndUserId(cartItemId, userId)
                .map(CartItemsServiceImpl::mapDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item not found for id: " + cartItemId + " and user id: " + userId));
    }

 @Override
@Transactional
public void deleteCartItemsByUser(Long cartItemId, Long userId) {
    CartItems cartItem = cartItemRepository.findByCartItemIdAndUserId(cartItemId, userId)
        .orElseThrow(() -> new EntityNotFoundException(
            "Cart item not found with id " + cartItemId + " and user id " + userId));

    this.cartItemRepository.delete(cartItem);
}


}
