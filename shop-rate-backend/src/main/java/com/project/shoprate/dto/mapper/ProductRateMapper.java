package com.project.shoprate.dto.mapper;

import com.project.shoprate.domain.ProductRate;
import com.project.shoprate.dto.ProductRateDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductRateMapper {

    public ProductRateDto toDto(ProductRate productRate) {
        return new ProductRateDto(productRate.getId(), productRate.getRate(), productRate.getProductType());
    }

    public List<ProductRateDto> toDtoList(List<ProductRate> productRateList) {
        return productRateList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ProductRate> toEntityList(List<ProductRateDto> productRateDtoList) {
        return productRateDtoList.stream().map(productRate ->
                new ProductRate(productRate.getId(), productRate.getRate(), productRate.getProductType()))
                .collect(Collectors.toList());
    }
}
