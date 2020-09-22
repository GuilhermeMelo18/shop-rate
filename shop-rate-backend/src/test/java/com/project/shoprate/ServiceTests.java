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
import java.util.List;

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
    public void calculate_total_prices_test() {

        ProductRate cosmeticsProductRate = productRateRepository.findByProductType(ProductType.COSMETICS).get(0);
        ProductRate foodProductRate = productRateRepository.findByProductType(ProductType.FOOD).get(0);
        ProductRate importedProductRate = productRateRepository.findByProductType(ProductType.IMPORTED).get(0);

        Product importedBoxChocolate = productRepository.save(new Product("Imported Box Chocolate",
                BigDecimal.valueOf(10.00), Arrays.asList(foodProductRate, importedProductRate)));
        Product importedCosmetics = productRepository.save(new Product("Imported Cosmetics",
                BigDecimal.valueOf(47.50), Arrays.asList(cosmeticsProductRate, importedProductRate)));

        ProductQueryParams queryParams = new ProductQueryParams();
        queryParams.setIds(Arrays.asList(importedBoxChocolate.getId(),importedCosmetics.getId()));
        ProductPricesWrapper productPrices = productService.getProductPrices(queryParams);

        productPrices.getProductList().forEach(product -> {
            if (product.getId().equals(importedBoxChocolate.getId())) {
                Assert.assertEquals(product.getPrice(), importedBoxChocolate.getTotalPrice());
            } else  {
                Assert.assertEquals(product.getPrice(), importedCosmetics.getTotalPrice());
            }
        });

        Assert.assertEquals(productPrices.getTotalPrices(), importedBoxChocolate.getTotalPrice().add(importedCosmetics.getTotalPrice()));
        Assert.assertEquals(productPrices.getTotalRatePrices(), importedBoxChocolate.getTotalRatePrice().add(importedCosmetics.getTotalRatePrice()));
    }

}
