package com.project.shoprate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.shoprate.util.StringPool;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    private Long id;

    @NotBlank(message = StringPool.REQUIRED_NAME_MESSAGE)
    private String name;

    @NotNull(message = StringPool.REQUIRED_PRICE_MESSAGE)
    private BigDecimal price;

    @NotEmpty(message = StringPool.REQUIRED_PRODUCT_RATE_MESSAGE)
    @Valid
    private List<ProductRateDto> productRateList;

    public ProductDto(Long id, String name, BigDecimal price, List<ProductRateDto> productRateList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productRateList = productRateList;
    }

    public ProductDto(String name, BigDecimal price, List<ProductRateDto> productRateList) {
        this.name = name;
        this.price = price;
        this.productRateList = productRateList;
    }

    public ProductDto(){}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public List<ProductRateDto> getProductRateList() { return productRateList; }

    public void setProductRateList(List<ProductRateDto> productRateList) { this.productRateList = productRateList; }
}
