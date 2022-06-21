package com.example.springbootmall.service.impl;

import com.example.springbootmall.constant.ProductCategory;
import com.example.springbootmall.dao.ProductDao;
import com.example.springbootmall.dto.ProductQueryParams;
import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;


    public List<Product> getProducts(ProductQueryParams productQueryParams){
        return productDao.getProducts(productQueryParams);
    }

    public Integer countProduct(ProductQueryParams productQueryParams){
        return  productDao.countProduct(productQueryParams);
    }

    @Override
    public Product getProductById(Integer productId) {


        return productDao.getProductById(productId);
    }


    public Integer createProduct(ProductRequst productRequst){
        return productDao.createProduct(productRequst);
    }

    public void updateProduct(Integer productId,ProductRequst productRequst){
        productDao.updateProduct(productId,productRequst);
    }

    public void deleteProductById(Integer productId){
        productDao.deleteProductById(productId);
    }
}
