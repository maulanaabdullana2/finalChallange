package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.AuthResponseDto;
import com.codeid.eshopay_backend.model.dto.UserDto;
import com.codeid.eshopay_backend.model.dto.userResponseDto;
import com.codeid.eshopay_backend.model.entity.Cart;
import com.codeid.eshopay_backend.model.entity.User;
import com.codeid.eshopay_backend.service.UserService;
import com.codeid.eshopay_backend.repository.CartRepsoitory;
import com.codeid.eshopay_backend.repository.userRepository;
import com.codeid.eshopay_backend.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepsoitory cartRepsoitory;
    private final JwtUtil jwtUtil;

    public static userResponseDto mapDto(User user) {
        return userResponseDto.builder()
                .userId(user.userId)
                .build();
    }

    public static User mapToEntity(userResponseDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .build();
    }

    @Override
    public List<UserDto> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public UserDto findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public UserDto save(UserDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public UserDto update(Long id, UserDto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public AuthResponseDto login(UserDto userDto) {
        User user = userRepository.findByUserEmail(userDto.getUserEmail());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(userDto.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getUserId());
        return new AuthResponseDto(token);
    }

    @Override
    public userResponseDto register(UserDto userDto) {

        if (!userDto.getUserPassword().equals(userDto.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }
        User user = userRepository.findByUserEmail(userDto.getUserEmail());
        if (user != null) {
            throw new RuntimeException("User already exists");
        }
        User userEntity = new User();
        userEntity.setUserEmail(userDto.getUserEmail());
        userEntity.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
        userEntity.setUserPhone(userDto.getUserPhone());
        User savedUser = userRepository.save(userEntity);
        // buat cart
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepsoitory.save(cart);

        return mapDto(savedUser);
    }

}
