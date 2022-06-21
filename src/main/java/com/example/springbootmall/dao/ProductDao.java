package com.example.springbootmall.dao;

import com.example.springbootmall.constant.ProductCategory;
import com.example.springbootmall.dto.ProductQueryParams;
import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductDao {


    Product getProductById(Integer productId);


    Integer createProduct(ProductRequst productRequst);

    void updateProduct(Integer productId , ProductRequst productRequst);

    void deleteProductById(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);
}
