package org.katrin.store.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.katrin.store.converter.ProductConverter;
import org.katrin.store.dto.ProductDto;
import org.katrin.store.entity.ProductEntity;
import org.katrin.store.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void getAllTest_ok() {
        List<ProductDto> productDtos = List.of(new ProductDto(), new ProductDto());
        List<ProductEntity> productEntities = productDtos.stream().map(ProductConverter::toEntity).toList();

        when(productRepository.findAll()).thenReturn(productEntities);

        List<ProductDto> actual = productService.getAll();

        assertEquals(actual, productDtos);
        assertEquals(actual.size(), productEntities.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getByIdTest_ok() {
        int id = 1;
        ProductDto productDto = ProductDto.builder().id(id).build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(productEntity));

        ProductDto actual = productService.getById(id);

        assertEquals(actual, productDto);
        assertEquals(actual.getId(), id);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    public void getByIdTest_notFound() {
        int id = 1;

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getById(id));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void saveTest_ok() {
        int id = 1;
        ProductDto productDto = ProductDto.builder().id(id).build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductDto actual = productService.save(productDto);

        assertEquals(actual, productDto);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void updateTest_ok() {
        int id = 1;
        ProductDto productDto = ProductDto.builder().id(id).build();
        ProductEntity productEntity = ProductConverter.toEntity(productDto);

        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductDto actual = productService.save(productDto);

        assertEquals(actual, productDto);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    public void deleteByIdTest_ok() {
        int id = 1;

        doNothing().when(productRepository).deleteById(id);
        productService.delete(id);

        verify(productRepository, times(1)).deleteById(id);
    }
}
