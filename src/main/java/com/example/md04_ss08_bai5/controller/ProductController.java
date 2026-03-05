package com.example.md04_ss08_bai5.controller;

import com.example.md04_ss08_bai5.entity.Product;
import com.example.md04_ss08_bai5.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private static final String UPLOAD_DIR = "uploads";
    private List<Product> products = new ArrayList<>();

    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("image")MultipartFile image
            ){
        if(name.isEmpty()){
            return new ResponseEntity<>("Tên sản phẩm không được để trống", HttpStatus.BAD_REQUEST);
        }

        try {
//
//            if(fileName == null || fileName.isEmpty()){
//                return new ResponseEntity<>("Ảnh sản phẩm không được để trống", HttpStatus.BAD_REQUEST);
//            }
//
//            String lowercaseFileName = fileName.toLowerCase();

            String contentType = image.getContentType();
            if(contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))){
                return new ResponseEntity<>("Chỉ nhận file ảnh jpeg/png", HttpStatus.BAD_REQUEST);
            }else{
                String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
                String newName = UUID.randomUUID() + "." + extension;

                Path uploadPath = Paths.get(UPLOAD_DIR);
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(newName);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                products.add(new Product(name, price, newName));

                return new ResponseEntity<>("Upload sản phẩm thành công", HttpStatus.OK);

            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi server", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(){
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
