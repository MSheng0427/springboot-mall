package com.example.springbootmall.dao.impl;


import com.example.springbootmall.dao.ProductDao;
import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql ="SELECT product_id,product_name, category, image_url, price, stock," +
                " description, created_date, last_modified_date " +
                "from product where product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());


        if(productList.size() > 0){

            return productList.get(0);
        }else{
            return  null;
        }


    }


    public Integer createProduct(ProductRequst productRequst) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock," +
                "description, created_date, last_modified_date) " +
                "VALUES (:product_name,:category,:image_url,:price,:stock," +
                ":description,:created_date,:last_modified_date)";

        Map<String, Object> map = new HashMap<>();
        map.put("product_name", productRequst.getProductName());
        map.put("category", productRequst.getCategory().toString());
        map.put("image_url", productRequst.getImageUrl());
        map.put("price", productRequst.getPrice());
        map.put("stock", productRequst.getStock());
        map.put("description", productRequst.getDescription());

        Date now = new Date();
        map.put("last_modified_date", now);
        map.put("created_date", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();
        return productId;


    }
}
