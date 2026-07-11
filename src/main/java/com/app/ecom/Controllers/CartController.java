package com.app.ecom.Controllers;

import com.app.ecom.Model.Dto.CartItemRequestDto;
import com.app.ecom.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId, @RequestBody CartItemRequestDto cartItemRequestDto) {
        boolean added = cartService.addToCart(userId, cartItemRequestDto);
        if(added) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Product Added successfully to cart");
        }
        return ResponseEntity.badRequest().body("Unable to Add product to cart");
    }

}
