package com.project.shoprate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.shoprate.domain.ProductType;
import com.project.shoprate.util.StringPool;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRateDto {

    private Long id;

    @NotNull(message = StringPool.REQUIRED_RATE_MESSAGE)
    private BigDecimal rate;

    @NotNull(message = StringPool.REQUIRED_PRODUCT_TYPE_MESSAGE)
    private ProductType productType;


    public ProductRateDto(Long id, BigDecimal rate, ProductType productType) {
        this.id = id;
        this.rate = rate;
        this.productType = productType;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public BigDecimal getRate() { return rate; }

    public void setRate(BigDecimal rate) { this.rate = rate; }

    public ProductType getProductType() { return productType; }

    public void setProductType(ProductType productType) { this.productType = productType; }
}
