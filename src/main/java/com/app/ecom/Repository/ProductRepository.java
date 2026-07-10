package com.app.ecom.Repository;

import com.app.ecom.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    @Query("""
    SELECT p
    FROM Product p
    WHERE p.active = true
      AND p.stockQuantity > 0
      AND LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    List<Product> searchProducts(String search);
}
