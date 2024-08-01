package org.katrin.store.converter;

import org.junit.jupiter.api.Test;
import org.katrin.store.dto.ProductDto;
import org.katrin.store.entity.ProductEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductConverterTest {

    @Test
    public void toDtoTest() {
        ProductEntity productEntity = ProductEntity.builder()
                .id(1)
                .name("Test Product")
                .stockQuantity(100)
                .country("Ukraine")
                .price(99.99)
                .build();

        ProductDto productDto = ProductConverter.toDto(productEntity);

        assertEquals(productEntity.getId(), productDto.getId());
        assertEquals(productEntity.getName(), productDto.getName());
        assertEquals(productEntity.getStockQuantity(), productDto.getStockQuantity());
        assertEquals(productEntity.getCountry(), productDto.getCountry());
        assertEquals(productEntity.getPrice(), productDto.getPrice());
    }

    @Test
    public void toEntityTest() {
        ProductDto productDto = ProductDto.builder()
                .id(1)
                .name("Test Product")
                .stockQuantity(100)
                .country("Ukraine")
                .price(99.99)
                .build();

        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        assertEquals(productDto.getId(), productEntity.getId());
        assertEquals(productDto.getName(), productEntity.getName());
        assertEquals(productDto.getStockQuantity(), productEntity.getStockQuantity());
        assertEquals(productDto.getCountry(), productEntity.getCountry());
        assertEquals(productDto.getPrice(), productEntity.getPrice());
    }
}
