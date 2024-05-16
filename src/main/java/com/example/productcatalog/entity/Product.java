package com.example.productcatalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private double price;
    private String image;
    @ManyToOne
    private Category category;
    private Date dateAdded;
    private boolean active = Boolean.FALSE;
}
