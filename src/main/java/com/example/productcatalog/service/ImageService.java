package com.example.productcatalog.service;

import com.example.productcatalog.entity.Image;
import com.example.productcatalog.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveImage(MultipartFile file, UUID productId){
        byte[] imageData = new byte[0];
        try {
            imageData = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = new Image();
        image.setData(imageData);
        image.setProduct(productId);
        image.setName(file.getName());
        imageRepository.save(image);
    }

    public void save(MultipartFile file) {
        byte[] imageData = new byte[0];
        try {
            imageData = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image image = new Image();
        image.setName(file.getName());
        image.setData(imageData);
        imageRepository.save(image);
    }

    public List<Image> getAllImage(){
        List<Image> images = imageRepository.findAll();
        return images;
    }


    public List<Image> getImagesByProductId(UUID productId) {
        return imageRepository.findByProduct(productId);
    }
}
