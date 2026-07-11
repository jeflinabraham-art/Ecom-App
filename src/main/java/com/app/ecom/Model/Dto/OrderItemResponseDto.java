package com.app.ecom.Model.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItemResponseDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
