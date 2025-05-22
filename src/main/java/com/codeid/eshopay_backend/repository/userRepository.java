package com.codeid.eshopay_backend.repository;

import com.codeid.eshopay_backend.model.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepository extends JpaRepository<User, Long> {
    @Query (value = "SELECT * FROM person.users WHERE user_email = ?1", nativeQuery = true)
    User findByUserEmail(String userEmail);
}
