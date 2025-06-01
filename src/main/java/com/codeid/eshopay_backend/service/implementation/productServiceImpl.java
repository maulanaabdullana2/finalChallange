package com.codeid.eshopay_backend.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.codeid.eshopay_backend.model.dto.ProductImageDto;
import com.codeid.eshopay_backend.model.dto.productDto;
import com.codeid.eshopay_backend.model.entity.Category;
import com.codeid.eshopay_backend.model.entity.Product;
import com.codeid.eshopay_backend.model.entity.ProductImage;
import com.codeid.eshopay_backend.model.entity.Supplier;
import com.codeid.eshopay_backend.repository.productImageRepository;
import com.codeid.eshopay_backend.repository.productRepository;
import com.codeid.eshopay_backend.service.productService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class productServiceImpl implements productService {

    private final productRepository productRepository;

    private final productImageRepository productImageRepository;

    public static productDto mapToDto(Product product) {
        return productDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .supplier(supplierServiceImpl.mapDto(product.getSupplier()))
                .category(categoryServiceImpl.mapToDto(product.getCategory()))
                .quantityPerUnit(product.getQuantityPerUnit())
                .unitPrice(product.getUnitPrice())
                .unitsInStock(product.getUnitsInStock())
                .unitsOnOrder(product.getUnitsOnOrder())
                .reorderLevel(product.getReorderLevel())
                .discontinued(product.getDiscontinued())
                .photo(product.getPhoto())
                .build();
    }

    public static Product mapToEntity(productDto productDto) {
        return Product.builder()
                .productId(productDto.getProductId())
                .productName(productDto.getProductName())
                .supplier(supplierServiceImpl.mapToEntity(productDto.getSupplier()))
                .category(categoryServiceImpl.mapToEntity(productDto.getCategory()))
                .quantityPerUnit(productDto.getQuantityPerUnit())
                .unitPrice(productDto.getUnitPrice())
                .unitsInStock(productDto.getUnitsInStock())
                .unitsOnOrder(productDto.getUnitsOnOrder())
                .reorderLevel(productDto.getReorderLevel())
                .discontinued(productDto.getDiscontinued())
                .photo(productDto.getPhoto())
                .build();
    }

    public static ProductImageDto mapToImageDto(ProductImage productImage) {
        return ProductImageDto.builder()
                .imageId(productImage.getImageId())
                .fileName(productImage.getFileName())
                .fileSize(productImage.getFileSize())
                .fileType(productImage.getFileType())
                .fileUri(productImage.getFileUri())
                .productId(productImage.getProduct().getProductId())
                .build();
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
        product.setCategory(new Category(entity.getCategory().getCategoryId(), entity.getCategory().getCategoryName(),
                entity.getCategory().getCategoryDescription()));
        product.setSupplier(new Supplier(entity.getSupplier().getSupplierId(), entity.getSupplier().getCompanyName()));
        product.setQuantityPerUnit(entity.getQuantityPerUnit());
        product.setUnitPrice(entity.getUnitPrice());
        product.setUnitsInStock(entity.getUnitsInStock());
        product.setUnitsOnOrder(entity.getUnitsOnOrder());
        product.setReorderLevel(entity.getReorderLevel());
        product.setDiscontinued(entity.getDiscontinued());
        product.setPhoto(entity.getPhoto());

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

    @Override
    public List<ProductImageDto> bulkFindAll(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<ProductImage> productImages = this.productImageRepository.findAllByProduct(product);

        return productImages.stream()
                .map(productServiceImpl::mapToImageDto)
                .toList();
    }

    @Override
    @Transactional
    public List<ProductImageDto> bulkCreate(Long id, MultipartFile[] files, List<String> filenames) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));

        List<ProductImage> productImages = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            String originalFileName = filenames.get(i);
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            ProductImage productImage = new ProductImage();
            productImage.setFileName(uniqueFileName);
            productImage.setFileSize(files[i].getSize());
            productImage.setFileType(files[i].getContentType());
            productImage.setFileUri("http://localhost:8088/api/product/view/" + filenames.get(i));
            productImage.setProduct(product);

            productImages.add(productImage);
        }

        this.productImageRepository.saveAll(productImages);

        return productImages.stream()
                .map(productServiceImpl::mapToImageDto)
                .toList();
    }

    @Override
    public void deleteImage(Long productId, Long imageId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ProductImage productImage = this.productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!productImage.getProduct().getProductId().equals(product.getProductId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image does not belong to the specified product");
        }
        this.productImageRepository.delete(productImage);
    }

    @Override
    public List<productDto> searchProduct(String productName, String categoryName, Pageable pageable) {
         return this.productRepository.searchProductsByNameAndCategory(productName, categoryName ,pageable)
                .stream().map(productServiceImpl::mapToDto).toList();
    }



}
