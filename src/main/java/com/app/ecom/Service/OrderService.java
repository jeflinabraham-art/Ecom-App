package com.app.ecom.Service;

import com.app.ecom.Enum.OrderStatus;
import com.app.ecom.Model.*;
import com.app.ecom.Model.Dto.OrderItemResponseDto;
import com.app.ecom.Model.Dto.OrderResponseDto;
import com.app.ecom.Repository.CartItemRepository;
import com.app.ecom.Repository.OrderRepository;
import com.app.ecom.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public Optional<OrderResponseDto> createOrder(String userId) {

        // check for user
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        // get the cart items
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if(cartItems.isEmpty()) {
            return Optional.empty();
        }

        // calculate total price for order
        // reduce() → combines all elements of a stream into a single result.
        BigDecimal totalPrice = cartItems
                .stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        // create order items
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());

                    // reduce product stock
                    Product product = cartItem.getProduct();
                    product.setStockQuantity(
                            product.getStockQuantity() - cartItem.getQuantity()
                    );
                    return orderItem;
                })
                .toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // clear cart
        cartItemRepository.deleteAll(cartItems);

        return Optional.ofNullable(toOrderResponseDto(savedOrder));
    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setOrderStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setItems(order.getItems().stream()
                .map(this::toOrderItemResponseDto)
                .toList());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        return dto;
    }

    public OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(orderItem.getId());
        dto.setPrice(orderItem.getPrice());
        dto.setQuantity(orderItem.getQuantity());
        dto.setProductId(orderItem.getProduct().getId());
        return dto;
    }
}
