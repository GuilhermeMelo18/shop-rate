package com.project.shoprate.controller;

import com.project.shoprate.queryParams.ProductQueryParams;
import com.project.shoprate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.BeanParam;

@RestController
@RequestMapping({"/product"})
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity findAll() {

        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping({"/get-product-prices"})
    public ResponseEntity getProductPrices(@BeanParam ProductQueryParams queryParams) {

        return ResponseEntity.ok().body(productService.getProductPrices(queryParams));
    }
}
