package com.project.shoprate;

import com.project.shoprate.domain.Product;
import com.project.shoprate.domain.ProductRate;
import com.project.shoprate.domain.ProductType;
import com.project.shoprate.repository.ProductRateRepository;
import com.project.shoprate.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class ShopRateApplication implements CommandLineRunner {


	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductRateRepository productRateRepository;

	public static void main(String[] args) {
		SpringApplication.run(ShopRateApplication.class, args);
	}

	@Override
	public void run(String ... args) throws Exception {

		List<ProductRate> productRateList = productRateRepository.findAll();

		if (CollectionUtils.isEmpty(productRateList)) {

			ProductRate bookProductRate = new ProductRate(BigDecimal.ZERO, ProductType.BOOK);
			ProductRate foodProductRate = new ProductRate(BigDecimal.ZERO, ProductType.FOOD);
			ProductRate medicalProductRate = new ProductRate(BigDecimal.ZERO, ProductType.MEDICAL);
			ProductRate musicProductRate = new ProductRate(BigDecimal.valueOf(10), ProductType.MUSIC);
			ProductRate cosmeticsProductRate = new ProductRate(BigDecimal.valueOf(10), ProductType.COSMETICS);
			ProductRate importedProductRate = new ProductRate(BigDecimal.valueOf(5), ProductType.IMPORTED);

			bookProductRate = productRateRepository.save(bookProductRate);
			foodProductRate = productRateRepository.save(foodProductRate);
			musicProductRate = productRateRepository.save(musicProductRate);
			productRateRepository.save(cosmeticsProductRate);
			productRateRepository.save(importedProductRate);
			productRateRepository.save(medicalProductRate);

			Product book = new Product("Book", BigDecimal.valueOf(12.49), Collections.singletonList(bookProductRate));
			Product musicCD = new Product("Music CD", BigDecimal.valueOf(14.99), Collections.singletonList(musicProductRate));
			Product chocolateBar = new Product("Chocolate Bar", BigDecimal.valueOf(0.85), Collections.singletonList(foodProductRate));

			productRepository.save(book);
			productRepository.save(musicCD);
			productRepository.save(chocolateBar);

		}
	}
}
