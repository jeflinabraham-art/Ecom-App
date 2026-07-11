package com.app.ecom.Service;

import com.app.ecom.Model.CartItem;
import com.app.ecom.Model.Dto.CartItemRequestDto;
import com.app.ecom.Model.Product;
import com.app.ecom.Model.User;
import com.app.ecom.Repository.CartItemRepository;
import com.app.ecom.Repository.ProductRepository;
import com.app.ecom.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
}
