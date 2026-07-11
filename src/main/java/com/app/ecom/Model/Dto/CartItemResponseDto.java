package com.app.ecom.Model.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CartItemResponseDto {

    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}