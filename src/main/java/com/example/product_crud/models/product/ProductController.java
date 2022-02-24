package com.example.product_crud.models.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "product_crud/api/v1/products")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Object> getProducts(){
        logger.info("GET PRODUCTS");
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> saveProducts(@RequestBody List<Product> products){
        logger.info("POST PRODUCTS");
        return new ResponseEntity<>(productService.insertProducts(products), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{product_id}")
    public ResponseEntity<Object> updateProducts(@PathVariable String product_id, @RequestBody Product product){
        logger.info("PUT PRODUCTS");
        try {
            return new ResponseEntity<>(productService.updateProduct(product_id, product), HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{product_id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String product_id){
        logger.info("DELETE PRODUCTS");
        productService.deleteProduct(product_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
