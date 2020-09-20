package com.project.shoprate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private ProductRateDto productRateDto;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public ProductRateDto getProductRateDto() { return productRateDto; }

    public void setProductRateDto(ProductRateDto productRateDto) { this.productRateDto = productRateDto; }
}
