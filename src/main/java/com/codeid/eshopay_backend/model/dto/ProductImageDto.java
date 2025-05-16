package com.codeid.eshopay_backend.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDto {

    private Long imageId;

    private String fileName;

    private Long fileSize;

    private String fileType;

    private String fileUri;

    private Long productId;
}