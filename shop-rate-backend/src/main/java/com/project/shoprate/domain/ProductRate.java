package com.project.shoprate.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class ProductRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal rate;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ProductType productType;

    public ProductRate(BigDecimal rate, ProductType productType) {
        this.rate = rate;
        this.productType = productType;
    }

    public ProductRate(Long id, BigDecimal rate, ProductType productType) {
        this.id = id;
        this.rate = rate;
        this.productType = productType;
    }

    public ProductRate() {}

    public Long getId() { return id; }

    public BigDecimal getRate() { return rate; }

    public void setRate(BigDecimal rate) { this.rate = rate; }

    public ProductType getProductType() { return productType; }

    public void setProductType(ProductType productType) { this.productType = productType; }
}
