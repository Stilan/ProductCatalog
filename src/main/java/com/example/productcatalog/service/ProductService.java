package com.example.productcatalog.service;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Image;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    public Product updateProduct(UUID productId, Product updatedProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setDateAdded(updatedProduct.getDateAdded());
        product.setActive(updatedProduct.isActive());

        return productRepository.save(product);
    }

    public List<Product> searchProducts(Category category, String name, Double minPrice, Double maxPrice) {
        UUID uuid = category == null ? null : category.getId();
        return productRepository.findByFilters(uuid, name, minPrice, maxPrice);
    }

   public List<Product> findAllByIdAndCategory(Category category) {
        return productRepository.findAllByCategory(category);
   }


}
