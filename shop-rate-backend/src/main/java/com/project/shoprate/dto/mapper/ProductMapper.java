package com.project.shoprate.dto.mapper;

import com.project.shoprate.domain.Product;
import com.project.shoprate.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());

        return productDto;
    }

}
