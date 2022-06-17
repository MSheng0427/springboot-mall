package com.example.springbootmall.service.impl;

import com.example.springbootmall.dao.ProductDao;
import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

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
}
