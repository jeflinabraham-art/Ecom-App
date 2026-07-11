package com.app.ecom.Repository;

import com.app.ecom.Model.CartItem;
import com.app.ecom.Model.Product;
import com.app.ecom.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);
    CartItem  deleteByUserAndProduct(User user, Product product);
    List<CartItem> findByUser(User user);
}
