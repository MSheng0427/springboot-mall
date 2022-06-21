package com.example.springbootmall.dao.impl;


import com.example.springbootmall.dao.ProductDao;
import com.example.springbootmall.dto.ProductQueryParams;
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


    /**
     * 查詢商品列表
     * @param productQueryParams
     * @return
     */
    public List<Product> getProducts(ProductQueryParams productQueryParams){
        String sql ="SELECT product_id,product_name, category, image_url, price, stock," +
                " description, created_date, last_modified_date " +
                "from product WHERE 1=1 ";

        Map<String,Object> map = new HashMap<>();

        // 判斷 category 是否有值 != null 加入 category 條件式
        // 查詢條件
        if(productQueryParams.getCategory() != null){
            sql = sql +" AND category = :category ";
            map.put("category" ,productQueryParams.getCategory().name());
        }

        if(productQueryParams.getSearch() != null){
            sql = sql +" AND  product_name LIKE :search ";
            map.put("search" ,"%"+productQueryParams.getSearch()+"%");
        }

        // 排序
        sql  = sql +" ORDER BY "+productQueryParams.getOrderBy() + " "+productQueryParams.getSort();

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset ";

        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
        return  productList;

    }

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

    /**
     * 新增商品
     * @param productRequst
     * @return
     */
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

    /**
     * 修改商品資料
     * @param productId :商品ID
     * @param productRequst : 修改商品資訊
     */
    public void updateProduct(Integer productId,ProductRequst productRequst){
        String sql  = "UPDATE product SET product_name=:product_name,category=:category,image_url=:image_url,price=:price,stock=:stock,"+
                      "description=:description,last_modified_date=:last_modified_date "+
                      "WHERE product_id = :product_id ";

        Map<String, Object> map = new HashMap<>();
        map.put("product_id",productId);
        map.put("product_name",productRequst.getProductName());
        map.put("category",productRequst.getCategory().toString());
        map.put("image_url",productRequst.getImageUrl());
        map.put("price",productRequst.getPrice());
        map.put("stock",productRequst.getStock());
        map.put("description",productRequst.getDescription());

        map.put("last_modified_date",new Date());

        namedParameterJdbcTemplate.update(sql,map);



    }

    public void deleteProductById(Integer productId ){
        String sql  = "Delete FROM product WHERE product_id =:product_id ";
        Map<String,Object> map = new HashMap<>();

        map.put("product_id",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
