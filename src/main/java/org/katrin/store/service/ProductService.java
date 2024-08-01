package org.katrin.store.service;

import lombok.AllArgsConstructor;
import org.katrin.store.converter.ProductConverter;
import org.katrin.store.dto.ProductDto;
import org.katrin.store.entity.ProductEntity;
import org.katrin.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class ProductService {
    private ProductRepository productRepository;

    public List<ProductDto> getAll() {
        return productRepository.findAll().stream().map(ProductConverter::toDto).toList();
    }

    public ProductDto getById(int id) {
        return productRepository.findById(id).map(ProductConverter::toDto).orElseThrow();
    }

    public ProductDto save(ProductDto productDto) {
        ProductEntity productEntity = productRepository.save(ProductConverter.toEntity(productDto));
        return ProductConverter.toDto(productEntity);
    }

    public ProductDto update(ProductDto productDto) {
        ProductEntity productEntity = productRepository.save(ProductConverter.toEntity(productDto));
        return ProductConverter.toDto(productEntity);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
