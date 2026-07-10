package com.app.ecom.Service;

import com.app.ecom.Model.Dto.ProductRequestDto;
import com.app.ecom.Model.Dto.ProductResponseDto;
import com.app.ecom.Model.Product;
import com.app.ecom.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = toEntity(productRequestDto);
        Product savedEntity = productRepository.save(product);
        return toResponseDto(savedEntity);
    }

    public Optional<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequestDto) {
        return productRepository.findById(id)
                .map(exisitingUser -> {
                    updateProductFromRequest(exisitingUser, productRequestDto);
                    productRepository.save(exisitingUser);
                    return toResponseDto(exisitingUser);
                });
    }

    public List<ProductResponseDto> getAllProducts() {
         return productRepository.findByActiveTrue()
                 .stream()
                 .map(this::toResponseDto)
                 .toList();
    }

    public boolean deleteProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponseDto> searchProducts(String search) {
        return productRepository.searchProducts(search)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public Product toEntity(ProductRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());

        return product;
    }

    public ProductResponseDto toResponseDto(Product savedProduct) {
        if (savedProduct == null) {
            return null;
        }

        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(savedProduct.getId());
        dto.setName(savedProduct.getName());
        dto.setDescription(savedProduct.getDescription());
        dto.setPrice(savedProduct.getPrice());
        dto.setStockQuantity(savedProduct.getStockQuantity());
        dto.setCategory(savedProduct.getCategory());
        dto.setImageUrl(savedProduct.getImageUrl());
        dto.setIsActive(savedProduct.getActive());

        return dto;
    }

    public void updateProductFromRequest(Product product, ProductRequestDto dto) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
    }
}
