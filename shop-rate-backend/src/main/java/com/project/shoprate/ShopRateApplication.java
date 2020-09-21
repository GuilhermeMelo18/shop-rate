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
import java.util.Arrays;
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
			medicalProductRate = productRateRepository.save(medicalProductRate);
			musicProductRate = productRateRepository.save(musicProductRate);
			cosmeticsProductRate = productRateRepository.save(cosmeticsProductRate);
			importedProductRate = productRateRepository.save(importedProductRate);

			Product book = new Product("Book", BigDecimal.valueOf(12.49), Collections.singletonList(bookProductRate));
			Product musicCD = new Product("Music CD", BigDecimal.valueOf(14.99), Collections.singletonList(musicProductRate));
			Product chocolateBar = new Product("Chocolate Bar", BigDecimal.valueOf(0.85), Collections.singletonList(foodProductRate));
			Product importedBoxChocolates = new Product("Imported Box of Chocolates", BigDecimal.valueOf(10.00), Arrays.asList(foodProductRate, importedProductRate));
			Product importedBottlePerfume = new Product("Imported Bottle of Perfume", BigDecimal.valueOf(47.50), Arrays.asList(cosmeticsProductRate, importedProductRate));
			Product importedBottlePerfume2 = new Product("Imported Bottle of Perfume", BigDecimal.valueOf(27.99), Arrays.asList(cosmeticsProductRate, importedProductRate));
			Product bottlePerfume = new Product("Bottle of Perfume", BigDecimal.valueOf(18.99), Collections.singletonList(cosmeticsProductRate));
			Product packetHeadachePills	= new Product("Packet of Headache Pills", BigDecimal.valueOf(9.75), Collections.singletonList(medicalProductRate));
			Product importedBoxChocolates2 = new Product("Imported Box of Chocolates", BigDecimal.valueOf(11.25), Arrays.asList(foodProductRate, importedProductRate));

			productRepository.save(book);
			productRepository.save(musicCD);
			productRepository.save(chocolateBar);
			productRepository.save(importedBoxChocolates);
			productRepository.save(importedBottlePerfume);
			productRepository.save(importedBottlePerfume2);
			productRepository.save(bottlePerfume);
			productRepository.save(packetHeadachePills);
			productRepository.save(importedBoxChocolates2);

		}
	}
}
