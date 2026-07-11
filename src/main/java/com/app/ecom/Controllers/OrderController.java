package com.app.ecom.Controllers;

import com.app.ecom.Model.Dto.OrderResponseDto;
import com.app.ecom.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestHeader("X-User-ID") String userId) {

        return orderService.createOrder(userId)
                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
