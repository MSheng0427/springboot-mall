package com.example.springbootmall.controller;


import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {



    @Autowired
    private ProductService productService;

    /**
     * 查詢商品
     * @param productId
     * @return
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            return  ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 新稱商品
     * @param productRequst
     * @return
     */
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequst productRequst){
        Integer productId = productService.createProduct(productRequst);
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

}
