package com.example.springbootmall.dao;

import com.example.springbootmall.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductDao {


    Product getProductById(Integer productId);
}
