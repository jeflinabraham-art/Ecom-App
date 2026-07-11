package com.app.ecom.Model.Dto;

import com.app.ecom.Enum.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemResponseDto> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
