package com.project.shoprate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPricesWrapper {

    private List<ProductDto> productDtoList;
    private BigDecimal totalRatePrices;
    private BigDecimal totalPrices;

    public ProductPricesWrapper() {
        this.productDtoList = new ArrayList<>();
        this.totalPrices = BigDecimal.ZERO;
        this.totalRatePrices = BigDecimal.ZERO;
    }

    public List<ProductDto> getProductDtoList() { return productDtoList; }

    public void setProductDtoList(List<ProductDto> productDtoList) { this.productDtoList = productDtoList; }

    public void addProductDtoList(ProductDto productDto) { this.productDtoList.add(productDto); }

    public BigDecimal getTotalPrices() { return totalPrices; }

    public void setTotalPrices(BigDecimal totalPrices) { this.totalPrices = totalPrices; }

    public void addTotalPrices(BigDecimal price) { this.totalPrices = this.totalPrices.add(price); }

    public BigDecimal getTotalRatePrices() { return totalRatePrices; }

    public void setTotalRatePrices(BigDecimal totalRatePrices) { this.totalRatePrices = totalRatePrices; }

    public void addTotalRatePrices(BigDecimal price) { this.totalRatePrices = this.totalRatePrices.add(price); }
}
