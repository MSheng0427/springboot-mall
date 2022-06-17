package com.example.springbootmall.service;

import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequst productRequst);

    void updateProduct(Integer productId,ProductRequst productRequst);
}
