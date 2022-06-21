package com.example.springbootmall.controller;


import com.example.springbootmall.constant.ProductCategory;
import com.example.springbootmall.dto.ProductQueryParams;
import com.example.springbootmall.dto.ProductRequst;
import com.example.springbootmall.model.Product;
import com.example.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated  // 驗證請求參數才會生效
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    /**
     *
     * @param category 商品類別
     * @param search 關鍵字查詢
     * @param orderBy 根據欄位排序 預設 根據created_date
     * @param sort 升序 降序  預設 降序
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category, // required = false :定義category 為非必要參數
            @RequestParam(required = false) String search,

            // 排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy, // defaultValue :設定預設值
            @RequestParam(defaultValue = "desc") String sort,

            // 分頁 Pagination

            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, // 傳入數值 不可 >1000 & <0
            @RequestParam(defaultValue = "0") @Min(0) Integer offset // 傳入數值不可 <0

    ){

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        List<Product> productList = productService.getProducts(productQueryParams);

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
