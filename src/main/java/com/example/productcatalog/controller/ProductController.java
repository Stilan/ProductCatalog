package com.example.productcatalog.controller;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Image;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.service.CategoryService;
import com.example.productcatalog.service.ImageService;
import com.example.productcatalog.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;
    private final CategoryService categoryService;

//    @RequestBody` используется, когда тело запроса должно быть связано с параметром метода, в то время как
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("product") String productJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            imageService.saveImage(file, product.getId());
            Product createdProduct = productService.createProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public List<ResponseEntity<byte[]>> getAllProductImages(@PathVariable UUID id) {
        List<Image> images = imageService.getAllImage();
        List<byte[]> imageData = images.stream()
                .map(Image::getData)
                .collect(Collectors.toList());
        List<ResponseEntity<byte[]>> imageResponses = new ArrayList<>();
        for (byte[] i : imageData) {
           imageResponses.add(ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(i.length)
                    .body(i));
        }
        return imageResponses;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    @PathVariable передать параметры в контроллер через путь URL
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
//    применяется в контроллерах для получения значений параметров запроса,
//    которые передаются в виде строк в URL-адресе или в теле запроса.
    @GetMapping
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String nameCategory,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        Category category = nameCategory == null ? null : categoryService.getCategoryName(nameCategory);
        List<Product> products = productService.searchProducts(category, name, minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
