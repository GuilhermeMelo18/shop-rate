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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<Product> findAll () {

        return productRepository.findAll();
    }

    public Optional<Product> find (Long id) {

        return productRepository.findById(id);
    }

    public List<Product> find(ProductQueryParams queryParams) {

        return productRepository
                .findAll(Specification.where(ProductSpecification.carriersInIds(queryParams.getIds())));
    }

    public void delete (Long id) {
        productRepository.deleteById(id);
    }

    public Product save (Product product) {
        return productRepository.save(product);
    }

    public ProductPricesWrapper getProductPrices(ProductQueryParams queryParams) {

        List<Product> products = find(queryParams);
        ProductPricesWrapper productPricesWrapper = new ProductPricesWrapper();

        products.forEach(product -> {
            ProductDto productDto = productMapper.toBasicDto(product);
            productDto.setPrice(product.getTotalPrice());
            productPricesWrapper.addProductList(productDto);
            productPricesWrapper.addTotalPrices(product.getTotalPrice());
            productPricesWrapper.addTotalRatePrices(product.getTotalRatePrice());
        });

        return productPricesWrapper;
    }
}
