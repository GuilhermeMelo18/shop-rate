package com.project.shoprate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.shoprate.domain.ProductType;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRateDto {

    private Long id;
    private BigDecimal rate;
    private ProductType productType;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public BigDecimal getRate() { return rate; }

    public void setRate(BigDecimal rate) { this.rate = rate; }

    public ProductType getProductType() { return productType; }

    public void setProductType(ProductType productType) { this.productType = productType; }
}
