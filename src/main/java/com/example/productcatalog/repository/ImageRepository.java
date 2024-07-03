package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends MongoRepository<Image, UUID> {

    List<Image> findByProduct(UUID productId);
}
