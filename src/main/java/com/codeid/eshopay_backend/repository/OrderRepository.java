package com.codeid.eshopay_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long>  {
}