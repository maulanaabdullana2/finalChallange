package com.codeid.eshopay_backend.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codeid.eshopay_backend.model.entity.Product;

@Repository
public interface productRepository extends JpaRepository<Product, Long> {
@Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) AND LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
Page<Product> searchProductsByNameAndCategory(@Param("keyword") String keyword, @Param("categoryName") String categoryName, Pageable pageable);


}
