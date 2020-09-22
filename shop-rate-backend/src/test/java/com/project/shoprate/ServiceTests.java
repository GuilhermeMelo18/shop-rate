package com.project.shoprate;

import com.project.shoprate.domain.Product;
import com.project.shoprate.domain.ProductRate;
import com.project.shoprate.domain.ProductType;
import com.project.shoprate.dto.ProductPricesWrapper;
import com.project.shoprate.queryParams.ProductQueryParams;
import com.project.shoprate.repository.ProductRateRepository;
import com.project.shoprate.repository.ProductRepository;
import com.project.shoprate.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ServiceTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRateRepository productRateRepository;

    @Test
    public void find_test() {

        ProductRate bookProductRate = productRateRepository.findByProductType(ProductType.BOOK).get(0);

        Product book = new Product("Book", BigDecimal.valueOf(12.49), Collections.singletonList(bookProductRate));
        book = productRepository.save(book);

        ProductQueryParams queryParams = new ProductQueryParams();
        queryParams.setIds(Collections.singletonList(book.getId()));
        List<Product> foundProductList = productService.find(queryParams);

        Assert.assertFalse(CollectionUtils.isEmpty(foundProductList));
        Assert.assertEquals(book.getId(), foundProductList.get(0).getId());

    }

    @Test
    public void calculate_total_prices_with_imported_products_test() {

        ProductRate cosmeticsProductRate = productRateRepository.findByProductType(ProductType.COSMETICS).get(0);
        ProductRate foodProductRate = productRateRepository.findByProductType(ProductType.FOOD).get(0);
        ProductRate importedProductRate = productRateRepository.findByProductType(ProductType.IMPORTED).get(0);

        Product importedBoxChocolate = productRepository.save(new Product("Imported Box Chocolate",
                BigDecimal.valueOf(10.00), Arrays.asList(foodProductRate, importedProductRate)));
        Product importedCosmetics = productRepository.save(new Product("Imported Cosmetics",
                BigDecimal.valueOf(47.50), Arrays.asList(cosmeticsProductRate, importedProductRate)));

        List<Product> productList = Arrays.asList(importedBoxChocolate, importedCosmetics);

        ProductQueryParams queryParams = new ProductQueryParams();
        queryParams.setIds(Arrays.asList(importedBoxChocolate.getId(),importedCosmetics.getId()));

        ProductPricesWrapper productPrices = productService.getProductPrices(queryParams);

        assertProductPrices(productPrices, productList);
    }

    @Test
    public void calculate_total_prices_with_untaxed_products_test() {

        ProductRate cosmeticsProductRate = productRateRepository.findByProductType(ProductType.COSMETICS).get(0);
        ProductRate importedProductRate = productRateRepository.findByProductType(ProductType.IMPORTED).get(0);
        ProductRate musicProductRate = productRateRepository.findByProductType(ProductType.MUSIC).get(0);
        ProductRate medicalProductRate = productRateRepository.findByProductType(ProductType.MEDICAL).get(0);
        ProductRate foodProductRate = productRateRepository.findByProductType(ProductType.FOOD).get(0);

        Product product01 = productRepository.save(new Product("Product 01",
                BigDecimal.valueOf(48.90), Arrays.asList(foodProductRate, importedProductRate)));
        Product product02 = productRepository.save(new Product("Product 02",
                BigDecimal.valueOf(100.20), Arrays.asList(cosmeticsProductRate, medicalProductRate)));
        Product product03 = productRepository.save(new Product("Product 03",
                BigDecimal.valueOf(36.70), Collections.singletonList(musicProductRate)));

        List<Product> productList = Arrays.asList(product01, product02, product03);

        ProductQueryParams queryParams = new ProductQueryParams();
        queryParams.setIds(Arrays.asList(product01.getId(), product02.getId(), product03.getId()));

        ProductPricesWrapper productPrices = productService.getProductPrices(queryParams);

        assertProductPrices(productPrices, productList);
    }


    private void assertProductPrices(ProductPricesWrapper productPrices, List<Product> productList){

        Map<Long, Product> productMap = new HashMap<>();
        BigDecimal totalPrices = BigDecimal.ZERO;
        BigDecimal totalRatePrices = BigDecimal.ZERO;

        for (Product product: productList) {
            productMap.put(product.getId(), product);
            totalPrices = totalPrices.add(product.getTotalPrice());
            totalRatePrices = totalRatePrices.add(product.getTotalRatePrice());
        }

        productPrices.getProductList().forEach( product -> {
            Assert.assertEquals(product.getPrice(), productMap.get(product.getId()).getTotalPrice());
        });

        Assert.assertEquals(productPrices.getTotalPrices(), totalPrices);
        Assert.assertEquals(productPrices.getTotalRatePrices(), totalRatePrices);
    }

}
