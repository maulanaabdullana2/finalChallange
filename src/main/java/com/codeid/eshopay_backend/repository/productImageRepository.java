package com.codeid.eshopay_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codeid.eshopay_backend.model.entity.Product;
import com.codeid.eshopay_backend.model.entity.ProductImage;

public interface productImageRepository extends JpaRepository<ProductImage,Long> {
List<ProductImage> findAllByProduct(Product product);
}