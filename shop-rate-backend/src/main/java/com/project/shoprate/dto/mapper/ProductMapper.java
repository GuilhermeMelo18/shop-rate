package com.project.shoprate.dto.mapper;

import com.project.shoprate.domain.Product;
import com.project.shoprate.domain.ProductRate;
import com.project.shoprate.dto.ProductDto;
import com.project.shoprate.dto.ProductRateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class ProductMapper {

    @Autowired
    ProductRateMapper productRateMapper;

    public ProductDto toBasicDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());

        return productDto;
    }

    public ProductDto toDto(Product product) {

        ProductDto productDto = toBasicDto(product);

        if (!CollectionUtils.isEmpty(product.getProductRateList())) {
           List<ProductRateDto> productRateDtoList = productRateMapper.toDtoList(product.getProductRateList());
           productDto.setProductRateList(productRateDtoList);
        }

        return productDto;
    }

    public Product toEntity(ProductDto productDto) {

        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        if (!CollectionUtils.isEmpty(productDto.getProductRateList())) {
            List<ProductRate> productRateList = productRateMapper.toEntityList(productDto.getProductRateList());
            product.setProductRateList(productRateList);
        }

        return product;
    }

}
