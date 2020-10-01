package com.project.shoprate.service;

import com.project.shoprate.domain.ProductRate;
import com.project.shoprate.repository.ProductRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRateService {

    @Autowired
    private ProductRateRepository productRateRepository;

    public List<ProductRate> findAll() {

        return productRateRepository.findAll();
    }

}
