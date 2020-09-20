package com.project.shoprate.service;

import com.project.shoprate.domain.Product;
import com.project.shoprate.dto.ProductDto;
import com.project.shoprate.dto.ProductPricesWrapper;
import com.project.shoprate.dto.mapper.ProductMapper;
import com.project.shoprate.queryParams.ProductQueryParams;
import com.project.shoprate.repository.ProductRepository;
import com.project.shoprate.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<Product> findAll () {

        return productRepository.findAll();
    }

    public List<Product> find(ProductQueryParams queryParams) {

        return productRepository
                .findAll(Specification.where(ProductSpecification.carriersInIds(queryParams.getIds())));
    }

    public ProductPricesWrapper getProductPrices(ProductQueryParams queryParams) {

        List<Product> products = find(queryParams);
        ProductPricesWrapper productPricesWrapper = new ProductPricesWrapper();

        products.forEach(product -> {
            ProductDto productDto = productMapper.toProductDto(product);
            productDto.setPrice(product.getTotalPrice());
            productPricesWrapper.addProductDtoList(productDto);
            productPricesWrapper.addTotalPrices(product.getTotalPrice());
            productPricesWrapper.addTotalRatePrices(product.getTotalRatePrice());
        });

        return productPricesWrapper;
    }
}
