package com.codeid.eshopay_backend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.codeid.eshopay_backend.model.dto.ProductImageDto;
import com.codeid.eshopay_backend.model.dto.productDto;

public interface productService extends BaseCrudService<productDto,Long>  {
     List<ProductImageDto> bulkFindAll(Long id);
     void deleteImage(Long productId, Long imageId);
     List<ProductImageDto> bulkCreate(Long id, MultipartFile[] files, List<String> filenames);
} 
