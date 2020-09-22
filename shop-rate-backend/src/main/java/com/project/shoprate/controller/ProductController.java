package com.project.shoprate.controller;

import com.project.shoprate.domain.Product;
import com.project.shoprate.dto.ProductDto;
import com.project.shoprate.dto.mapper.ProductMapper;
import com.project.shoprate.queryParams.ProductQueryParams;
import com.project.shoprate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.Optional;

@RestController
@RequestMapping({"/product"})
public class ProductController {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity findAll() {

        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity find( @PathVariable Long id) {

        Optional<Product> optProduct = productService.find(id);
        if (optProduct.isPresent()) {
            return ResponseEntity.ok().body(optProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity delete(@PathVariable Long id) {

        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        productDto = productMapper.toDto(productService.save(product));
        return ResponseEntity.ok().body(productDto);
    }

    @GetMapping({"/get-product-prices"})
    public ResponseEntity getProductPrices(@BeanParam ProductQueryParams queryParams) {

        return ResponseEntity.ok().body(productService.getProductPrices(queryParams));
    }
}
