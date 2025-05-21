package com.codeid.eshopay_backend.repository;

import com.codeid.eshopay_backend.model.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);
}
