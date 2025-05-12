package com.codeid.eshopay_backend.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codeid.eshopay_backend.model.dto.productDto;
import com.codeid.eshopay_backend.model.entity.Product;
import com.codeid.eshopay_backend.repository.productRepository;
import com.codeid.eshopay_backend.service.productService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class productServiceImpl implements productService {

    private final productRepository productRepository;

    public static productDto mapToDto(Product product) {
        return new productDto(
                product.getProductId(),
                product.getProductName(),
                supplierServiceImpl.mapDto(product.getSupplier()),
                categoryServiceImpl.mapToDto(product.getCategory()),
                product.getQuantityPerUnit(),
                product.getUnitPrice(),
                product.getUnitsInStock(),
                product.getUnitsOnOrder(),
                product.getReorderLevel(),
                product.getDiscontinued());
    }

    private Product mapToEntity(productDto productDto) {
        return new Product(productDto.getProductId(), productDto.getProductName(),
                supplierServiceImpl.mapToEntity(productDto.getSupplier()),
                categoryServiceImpl.mapToEntity(productDto.getCategory()),
                productDto.getQuantityPerUnit(),
                productDto.getUnitPrice(),
                productDto.getUnitsInStock(),
                productDto.getUnitsOnOrder(),
                productDto.getReorderLevel(),
                productDto.getDiscontinued());
    }

    @Override
    public List<productDto> findAll() {
        log.debug("Request Fetching Data Categories");
        return this.productRepository.findAll()
                .stream()
                .map(productServiceImpl::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public productDto findById(Long id) {
        log.debug("Request to get Department : {}", id);

        return this.productRepository.findById(id).map(productServiceImpl::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
    }

    @Override
    public productDto save(productDto entity) {
        log.debug("Request to create Employee : {}", entity);

        var product = mapToEntity(entity);

        return mapToDto(this.productRepository.save(product));

    }

    @Override
    public productDto update(Long id, productDto entity) {
        log.debug("Request to update Product : {}", id);

        var product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        product.setProductName(entity.getProductName());
        product.setCategory(categoryServiceImpl.mapToEntity(entity.getCategory()));
        product.setSupplier(supplierServiceImpl.mapToEntity(entity.getSupplier()));
        product.setQuantityPerUnit(entity.getQuantityPerUnit());
        product.setUnitPrice(entity.getUnitPrice());
        product.setUnitsInStock(entity.getUnitsInStock());
        product.setUnitsOnOrder(entity.getUnitsOnOrder());
        product.setReorderLevel(entity.getReorderLevel());
        product.setDiscontinued(entity.getDiscontinued());

        product = this.productRepository.save(product);

        return mapToDto(product);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);

        var product = this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find product with id " + id));

        this.productRepository.delete(product);

    }

}
