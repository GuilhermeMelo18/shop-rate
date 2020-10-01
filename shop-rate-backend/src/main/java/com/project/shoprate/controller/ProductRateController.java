package com.project.shoprate.controller;

import com.project.shoprate.domain.ProductRate;
import com.project.shoprate.dto.mapper.ProductRateMapper;
import com.project.shoprate.service.ProductRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/productRate"})
public class ProductRateController {

    @Autowired
    ProductRateService productRateService;

    @Autowired
    ProductRateMapper productRateMapper;

    @GetMapping
    public ResponseEntity findAll() {

        List<ProductRate> productRateList = productRateService.findAll();
        return ResponseEntity.ok().body(productRateMapper.toDtoList(productRateList));
    }

}
