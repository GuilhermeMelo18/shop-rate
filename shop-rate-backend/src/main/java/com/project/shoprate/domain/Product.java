package com.project.shoprate.domain;

import com.project.shoprate.util.StringPool;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = StringPool.REQUIRED_NAME_MESSAGE)
    private String name;

    @NotNull(message = StringPool.REQUIRED_PRICE_MESSAGE)
    private BigDecimal price;

    @ManyToMany
    @NotNull(message = StringPool.REQUIRED_PRODUCT_RATE_MESSAGE)
    @Valid
    @JoinTable(name = "product_product_rate", joinColumns = {
            @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "product_rate_id") })
    private List<ProductRate> productRateList;

    @Transient
    private BigDecimal totalRatePrice;

    public Product(String name, BigDecimal price, List<ProductRate> productRateList) {
        this.name = name;
        this.price = price;
        this.productRateList = productRateList;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public List<ProductRate> getProductRateList() { return productRateList; }

    public void setProductRateList(List<ProductRate> productRateList) { this.productRateList = productRateList; }

    public BigDecimal getTotalPrice() { return price.add(getTotalRatePrice()); }

    public BigDecimal getTotalRatePrice() {

        if (totalRatePrice != null)
            return totalRatePrice;

        totalRatePrice = productRateList.stream().map(productRate ->  {
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
