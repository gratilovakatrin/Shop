package org.katrin.store.converter;

import org.katrin.store.dto.ProductDto;
import org.katrin.store.entity.ProductEntity;

public class ProductConverter {
    public static ProductDto toDto(ProductEntity productEntity) {
        return ProductDto.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .stockQuantity(productEntity.getStockQuantity())
                .country(productEntity.getCountry())
                .price(productEntity.getPrice())
                .build();
    }

    public static ProductEntity toEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .stockQuantity(productDto.getStockQuantity())
                .country(productDto.getCountry())
                .price(productDto.getPrice())
                .build();
    }
}
