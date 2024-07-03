package com.example.productcatalog.controller;


import com.example.productcatalog.entity.Image;
import com.example.productcatalog.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    @PostMapping
    public void createProduct(@RequestParam("file") MultipartFile file) {
        imageService.save(file);
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImage();

        return new ResponseEntity<>(images, HttpStatus.OK);
    }

}
