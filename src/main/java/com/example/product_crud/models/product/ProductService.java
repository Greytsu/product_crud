package com.example.product_crud.models.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> getProductByID(String id){
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByURL(String url){
        return productRepository.findProductByUrl(url);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> insertProducts(List<Product> products){
        for (Product product : products) {
            var optProduct = getProductByURL(product.getUrl());
            optProduct.ifPresent(value -> product.setId(value.getId()));
        }

        for (Product product : products) {
            System.out.println(product.toString());
        }

        return productRepository.saveAll(products);
    }

    public Product updateProduct(String product_id, Product product) throws Exception {
        if(product_id.equals(product.getId()) && getProductByID(product_id).isPresent()){
            return productRepository.save(product);
        }
        throw new Exception("Product not found");
    }

    public void deleteProduct(String id){
        Optional<Product> optProduct = productRepository.findById(id);
        optProduct.ifPresent(productRepository::delete);
    }

}
