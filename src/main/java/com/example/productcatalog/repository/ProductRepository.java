package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product,UUID> {

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) ")
    List<Product> findByFilters(
            @Param("categoryId") UUID categoryId,
            @Param("name") String name,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice
    );

    List<Product> findAllByCategory(Category category);
}
