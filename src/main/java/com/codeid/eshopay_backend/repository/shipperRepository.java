package com.codeid.eshopay_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Shipper;


@Repository
public interface shipperRepository extends JpaRepository<Shipper,Long> {  
} 
