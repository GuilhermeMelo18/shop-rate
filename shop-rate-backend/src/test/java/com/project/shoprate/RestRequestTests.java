package com.project.shoprate;

import com.project.shoprate.domain.ProductType;
import com.project.shoprate.dto.ProductDto;
import com.project.shoprate.dto.ProductRateDto;
import com.project.shoprate.dto.mapper.ProductMapper;
import com.project.shoprate.dto.mapper.ProductRateMapper;
import com.project.shoprate.repository.ProductRateRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestRequestTests {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRateRepository productRateRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRateMapper productRateMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void save_product_test() {

        ProductRateDto productRateDto = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.BOOK).get(0));

        ProductDto productDto =
                new ProductDto("Product Test", BigDecimal.valueOf(12.49), Collections.singletonList(productRateDto));

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", productDto,
                ProductDto.class);

        assertProduct(productDto, postResponse.getBody());
    }

    @Test
    public void save_product_without_required_product_rate_test() {

        ProductDto productDto = new ProductDto(null, BigDecimal.valueOf(12.49), null);

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", productDto,
                ProductDto.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
    }

    private void assertProduct(ProductDto product, ProductDto savedProduct) {

        Assert.assertNotNull(savedProduct);

        Assert.assertEquals(product.getName(), savedProduct.getName());
        Assert.assertEquals(product.getPrice(), savedProduct.getPrice());

        Map<Long, ProductRateDto> productRateMap = new HashMap<>();

        product.getProductRateList().forEach(productRate -> productRateMap.put(productRate.getId(), productRate));

        savedProduct.getProductRateList().forEach( productRateSaved -> {
            ProductRateDto productRate = productRateMap.get(productRateSaved.getId());
            Assert.assertEquals(productRateSaved.getProductType(), productRate.getProductType());
            Assert.assertEquals(productRateSaved.getRate(), productRate.getRate());
        });
    }
}
