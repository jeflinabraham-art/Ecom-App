package com.app.ecom.Service;

import com.app.ecom.Model.CartItem;
import com.app.ecom.Model.Dto.CartItemRequestDto;
import com.app.ecom.Model.Dto.CartItemResponseDto;
import com.app.ecom.Model.Product;
import com.app.ecom.Model.User;
import com.app.ecom.Repository.CartItemRepository;
import com.app.ecom.Repository.ProductRepository;
import com.app.ecom.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequestDto cartItemRequestDto) {

        // check if product exists
        Optional<Product> productOpt = productRepository.findById(cartItemRequestDto.getProductId());
        if(productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();

        // check enough stock is available for the product.
        if(product.getStockQuantity() < cartItemRequestDto.getQuantity()) {
            return false;
        }

        // product and stock is available to be added to cart. Get the user.
        Optional<User> userOpt = userRepository.findById(Long.parseLong(userId));

        if(userOpt.isEmpty()) {
            return false;
        }

        User user =  userOpt.get();

        // Add to cart
        // case 1: if product already exists in cart
        CartItem exisitingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(exisitingCartItem != null) {
            exisitingCartItem.setQuantity(exisitingCartItem.getQuantity() + cartItemRequestDto.getQuantity());
            exisitingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(exisitingCartItem.getQuantity())));
            cartItemRepository.save(exisitingCartItem);
        }
        // case 2: product does not exist in cart, to be newly created.
        else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequestDto.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    // INSERT, UPDATE, DELETE → modify data → require a transaction.
    // transaction will already exist for build-in JPA functions like delete(), save()...
    @Transactional
    public boolean deleteFromCart(String UserId, Long ProductId) {
        Optional<Product> productOpt = productRepository.findById(ProductId);
        if(productOpt.isEmpty()) {
            return false;
        }
        Product product = productOpt.get();

        Optional<User> userOpt = userRepository.findById(Long.valueOf(UserId));
        if(userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();

        CartItem cartItem = cartItemRepository.deleteByUserAndProduct(user, product);
        return true;
    }

    public List<CartItemResponseDto> getCartItems(String userId) {
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()) {
            return new ArrayList<>();
        }
        User user = userOpt.get();
        return cartItemRepository.findByUser(user)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public CartItemResponseDto toResponseDto(CartItem cartItem) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setId(cartItem.getId());
        cartItemResponseDto.setUserId(cartItem.getUser().getId());
        cartItemResponseDto.setProductId(cartItem.getProduct().getId());
        cartItemResponseDto.setProductName(cartItem.getProduct().getName());
        cartItemResponseDto.setQuantity(cartItem.getQuantity());
        cartItemResponseDto.setPrice(cartItem.getPrice());
        cartItemResponseDto.setCreatedAt(cartItem.getCreatedAt());
        cartItemResponseDto.setUpdatedAt(cartItem.getUpdatedAt());
        return cartItemResponseDto;
    }

}
