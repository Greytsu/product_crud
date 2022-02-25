package com.example.product_crud.models.product;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
@ActiveProfiles("test")
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProductTest(){
        productRepository.save(
            Product.builder()
                .url("https://www.ldlc.com/fr-be/fiche/PB00438961.html")
                .name("Gigabyte GeForce RTX 3070 Ti GAMING OC 8G")
                .price(1069.95)
                .description("La carte graphique Gigabyte GeForce RTX 3070 Ti GAMING OC 8G")
                .stock("En stock")
                .build()
        );

        var optProduct = productRepository.findProductByUrl("https://www.ldlc.com/fr-be/fiche/PB00438961.htmla");
        if(optProduct.isPresent())
            assertThat(optProduct).isPresent();
    }

    @Test
    public void saveProductTest(){
        Product product = Product.builder()
            .url("https://www.ldlc.com/fr-be/fiche/PB00454442.html")
            .name("KFA2 GeForce RTX 3080 SG (1-Click OC) LHR")
            .price(1199.94)
            .description("La carte graphique gaming KFA2 GeForce RTX 3080 SG (1-Click OC)")
            .stock("Rupture")
            .build();

        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct).usingRecursiveComparison().ignoringFields("id").isEqualTo(product);
    }

    @Test
    public void updateProductTest(){
        Product product = Product.builder()
            .url("https://www.ldlc.com/fr-be/fiche/PB00454442.html")
            .name("KFA2 GeForce RTX 3080 SG (1-Click OC) LHR")
            .price(1199.94)
            .description("La carte graphique gaming KFA2 GeForce RTX 3080 SG (1-Click OC)")
            .stock("Rupture")
            .build();

        Product savedProduct = productRepository.save(product);
        savedProduct.setName("TEST UPDATE");
        savedProduct = productRepository.save(savedProduct);
        assertThat(savedProduct.getName()).isEqualTo("TEST UPDATE");
    }

    @Test
    public void deleteProductTest(){
        Product product = Product.builder()
            .url("https://www.ldlc.com/fr-be/fiche/PB00454442.html")
            .name("KFA2 GeForce RTX 3080 SG (1-Click OC) LHR")
            .price(1199.94)
            .description("La carte graphique gaming KFA2 GeForce RTX 3080 SG (1-Click OC)")
            .stock("Rupture")
            .build();

        Product savedProduct = productRepository.save(product);
        productRepository.delete(savedProduct);
        var optProduct = productRepository.findProductByUrl("https://www.ldlc.com/fr-be/fiche/PB00454442.html");
        assertThat(optProduct).isEmpty();
    }
}
