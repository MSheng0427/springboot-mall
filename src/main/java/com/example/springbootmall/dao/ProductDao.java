package com.example.springbootmall.dao;

import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductDao {


    Product getProductById(Integer productId);


    Integer createProduct(ProductRequst productRequst);

    void updateProduct(Integer productId , ProductRequst productRequst);
}
