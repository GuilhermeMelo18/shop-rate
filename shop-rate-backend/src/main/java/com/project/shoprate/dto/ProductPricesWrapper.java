package com.project.shoprate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPricesWrapper {

    private List<ProductDto> productList;
    private BigDecimal totalRatePrices;
    private BigDecimal totalPrices;

    public ProductPricesWrapper() {
        this.productList = new ArrayList<>();
        this.totalPrices = BigDecimal.ZERO;
        this.totalRatePrices = BigDecimal.ZERO;
    }

    public List<ProductDto> getProductList() { return productList; }

    public void setProductList(List<ProductDto> productList) { this.productList = productList; }

    public void addProductList(ProductDto productDto) { this.productList.add(productDto); }

    public BigDecimal getTotalPrices() { return totalPrices; }

    public void setTotalPrices(BigDecimal totalPrices) { this.totalPrices = totalPrices; }

    public void addTotalPrices(BigDecimal price) { this.totalPrices = this.totalPrices.add(price); }

    public BigDecimal getTotalRatePrices() { return totalRatePrices; }

    public void setTotalRatePrices(BigDecimal totalRatePrices) { this.totalRatePrices = totalRatePrices; }

    public void addTotalRatePrices(BigDecimal price) { this.totalRatePrices = this.totalRatePrices.add(price); }
}
