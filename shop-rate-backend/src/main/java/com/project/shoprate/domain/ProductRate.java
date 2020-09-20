package com.project.shoprate.domain;

import com.project.shoprate.util.StringPool;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public class ProductRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = StringPool.REQUIRED_RATE_MESSAGE)
    private BigDecimal rate;

    @NotNull(message = StringPool.REQUIRED_PRODUCT_TYPE_MESSAGE)
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ProductType productType;

    public ProductRate(BigDecimal rate, ProductType productType) {
        this.rate = rate;
        this.productType = productType;
    }

    public Long getId() { return id; }

    public BigDecimal getRate() { return rate; }

    public void setRate(BigDecimal rate) { this.rate = rate; }

    public ProductType getProductType() { return productType; }

    public void setProductType(ProductType productType) { this.productType = productType; }
}
