package com.project.shoprate.repository;

import com.project.shoprate.domain.ProductRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRateRepository extends JpaRepository<ProductRate, Long>, JpaSpecificationExecutor<ProductRate> {
}
