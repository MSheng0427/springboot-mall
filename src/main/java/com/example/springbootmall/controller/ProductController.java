package com.example.springbootmall.controller;


import com.example.springbootmall.constant.ProductCategory;
import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    /**
     *
     * @param category 商品類別
     * @param search 關鍵字查詢
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct(
            @RequestParam(required = false)  ProductCategory category, // required = false :定義category 為非必要參數
            @RequestParam(required = false)  String search
    ){
        List<Product> productList = productService.getProducts(category,search);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    /**
     * 查詢商品
     * @param productId
     * @return
     */
    @GetMapping("/products/{productId}")
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
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequst productRequst){
        Integer productId = productService.createProduct(productRequst);
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }


    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequst productRequst){

        // 檢查商品是否存在
        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 修改商品的資料
        productService.updateProduct(productId,productRequst);
        Product updateProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);





    }

    /**
     * 刪除商品
     * @param productId
     * @return
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
