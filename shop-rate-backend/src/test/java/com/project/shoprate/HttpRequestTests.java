package com.project.shoprate;

import com.project.shoprate.domain.ProductType;
import com.project.shoprate.dto.ProductDto;
import com.project.shoprate.dto.ProductPricesWrapper;
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
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests {

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
    public void delete_product_test() {

        ProductRateDto productRateDto = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.BOOK).get(0));

        ProductDto productDto = new ProductDto("Product", BigDecimal.valueOf(132.21), Collections.singletonList(productRateDto));

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", productDto,
                ProductDto.class);

        Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        Assert.assertNotNull(postResponse.getBody());

        restTemplate.delete(getRootUrl() + "/product/" + postResponse.getBody().getId(), ProductDto.class);

        ResponseEntity<ProductDto> findResponse =
                restTemplate.getForEntity(getRootUrl() + "/product/" + postResponse.getBody().getId(), ProductDto.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, findResponse.getStatusCode());
    }

    @Test
    public void find_product_test() {

        ProductRateDto productRateDto = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.IMPORTED).get(0));

        ProductDto productDto = new ProductDto("Product", BigDecimal.valueOf(130.90), Collections.singletonList(productRateDto));

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", productDto,
                ProductDto.class);

        Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        Assert.assertNotNull(postResponse.getBody());

        ResponseEntity<ProductDto> findResponse =
                restTemplate.getForEntity(getRootUrl() + "/product/" + postResponse.getBody().getId(), ProductDto.class);

        Assert.assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        Assert.assertNotNull(findResponse.getBody());

        Assert.assertEquals(postResponse.getBody().getId(), findResponse.getBody().getId());
    }

    @Test
    public void save_product_without_required_product_rate_test() {

        ProductDto productDto = new ProductDto(null, BigDecimal.valueOf(12.49), null);

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", productDto,
                ProductDto.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
    }

    @Test
    public void save_product_without_required_fields_test() {

        ProductRateDto productRateDto = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.COSMETICS).get(0));
        productRateDto.setRate(null);
        productRateDto.setProductType(null);

        ProductDto productDto = new ProductDto("Product", null, Collections.singletonList(productRateDto));

        ResponseEntity<ProductDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/product", productDto,
                ProductDto.class);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
    }

    @Test
    public void calculate_product_total_prices_with_imported_products_test() {

        ProductRateDto productRateDto01 = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.COSMETICS).get(0));
        ProductRateDto productRateDto02 = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.IMPORTED).get(0));
        ProductRateDto productRateDto03 = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.FOOD).get(0));
        ProductRateDto productRateDto04 = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.IMPORTED).get(0));

        ProductDto productDto01 = new ProductDto("Product01", BigDecimal.valueOf(153.37), Arrays.asList(productRateDto01, productRateDto02));
        ProductDto productDto02 = new ProductDto("Product02", BigDecimal.valueOf(132.21), Arrays.asList(productRateDto03, productRateDto04));

        ResponseEntity<ProductDto> productResponse01 = restTemplate.postForEntity(getRootUrl() + "/product", productDto01,
                ProductDto.class);

        ResponseEntity<ProductDto> productResponse02 = restTemplate.postForEntity(getRootUrl() + "/product", productDto02,
                ProductDto.class);

        Assert.assertNotNull(productResponse01.getBody());
        Assert.assertNotNull(productResponse02.getBody());

        String queryParams = generateQueryParams("ids",
                Arrays.asList(productResponse01.getBody().getId(), productResponse02.getBody().getId()));

        ResponseEntity<ProductPricesWrapper> productPricesResponse =
                restTemplate.getForEntity(getRootUrl() + "/product/get-product-prices" + queryParams, ProductPricesWrapper.class);

        Assert.assertNotNull(productPricesResponse.getBody());
        assertProductPrices(productPricesResponse.getBody(), Arrays.asList(productResponse01.getBody(), productResponse02.getBody()));
    }

    @Test
    public void calculate_product_total_prices_with_untaxed_products_test() {

        ProductRateDto productRateDto01 = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.FOOD).get(0));
        ProductRateDto productRateDto02 = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.MEDICAL).get(0));
        ProductRateDto productRateDto03 = productRateMapper.toDto(productRateRepository.findByProductType(ProductType.BOOK).get(0));

        ProductDto productDto01 = new ProductDto("Product01", BigDecimal.valueOf(153.37), Arrays.asList(productRateDto01, productRateDto02));
        ProductDto productDto02 = new ProductDto("Product02", BigDecimal.valueOf(132.21), Collections.singletonList(productRateDto03));

        ResponseEntity<ProductDto> productResponse01 = restTemplate.postForEntity(getRootUrl() + "/product", productDto01,
                ProductDto.class);

        ResponseEntity<ProductDto> productResponse02 = restTemplate.postForEntity(getRootUrl() + "/product", productDto02,
                ProductDto.class);

        Assert.assertNotNull(productResponse01.getBody());
        Assert.assertNotNull(productResponse02.getBody());

        String queryParams = generateQueryParams("ids",
                Arrays.asList(productResponse01.getBody().getId(), productResponse02.getBody().getId()));

        ResponseEntity<ProductPricesWrapper> productPricesResponse =
                restTemplate.getForEntity(getRootUrl() + "/product/get-product-prices" + queryParams, ProductPricesWrapper.class);

        Assert.assertNotNull(productPricesResponse.getBody());
        assertProductPrices(productPricesResponse.getBody(), Arrays.asList(productResponse01.getBody(), productResponse02.getBody()));
    }

    private String generateQueryParams(String paramsName, List<Long> paramValues) {
        StringBuilder queryParams = new StringBuilder("?");

        paramValues.forEach(paramValue ->
                queryParams.append(paramsName)
                        .append("=")
                        .append(paramValue)
                        .append("&"));

        return queryParams.toString();
    }

    private void assertProductPrices(ProductPricesWrapper productPrices, List<ProductDto> productList){

        Map<Long, BigDecimal> totalPriceMap = new HashMap<>();
        BigDecimal totalPrices = BigDecimal.ZERO;
        BigDecimal totalRatePrices = BigDecimal.ZERO;

        for (ProductDto product: productList) {
            BigDecimal totalPrice = getTotalPrice(product.getPrice(), product.getProductRateList());
            BigDecimal totalRatePrice = getTotalRatePrice(product.getPrice(), product.getProductRateList());
            totalPriceMap.put(product.getId(), totalPrice);
            totalPrices = totalPrices.add(totalPrice);
            totalRatePrices = totalRatePrices.add(totalRatePrice);
        }

        productPrices.getProductList().forEach( product -> {
            Assert.assertEquals(product.getPrice(), totalPriceMap.get(product.getId()));
        });

        Assert.assertEquals(productPrices.getTotalPrices(), totalPrices);
        Assert.assertEquals(productPrices.getTotalRatePrices(), totalRatePrices);
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

    public BigDecimal getTotalPrice(BigDecimal price, List<ProductRateDto> productRateDtoList) {
        return price.add(getTotalRatePrice(price, productRateDtoList));
    }

    public BigDecimal getTotalRatePrice(BigDecimal price, List<ProductRateDto> productRateList) {

        BigDecimal totalRatePrice = productRateList.stream().map(productRate ->  {
            if(productRate.getRate().equals(BigDecimal.ZERO)) {
                return BigDecimal.ZERO;
            }
            return price.multiply(productRate.getRate()
                    .divide(BigDecimal.valueOf(100))).setScale(2, RoundingMode.HALF_UP);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

        totalRatePrice =  BigDecimal.valueOf(Math.ceil(totalRatePrice.doubleValue() * 20) / 20);
        totalRatePrice = totalRatePrice.setScale(2, RoundingMode.HALF_UP);

        return totalRatePrice;
    }


}
