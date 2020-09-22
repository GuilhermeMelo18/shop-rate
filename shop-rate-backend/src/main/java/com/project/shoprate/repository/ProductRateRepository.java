package com.project.shoprate.repository;

import com.project.shoprate.domain.ProductRate;
import com.project.shoprate.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRateRepository extends JpaRepository<ProductRate, Long>, JpaSpecificationExecutor<ProductRate> {

    public List<ProductRate> findByProductType(ProductType productType);
}
