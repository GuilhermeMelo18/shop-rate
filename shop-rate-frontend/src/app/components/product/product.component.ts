import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/model/product';
import { ProductPricesWrapper } from 'src/app/model/product-prices-wrapper';
import { ProductRate } from 'src/app/model/product-rate';
import { ProductQueryParams } from 'src/app/model/query-params/product-query-params';
import { ProductRateService } from 'src/app/services/product-rate.service';
import { ProductService } from 'src/app/services/product.service';
@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  productList: Array<Product>;
  productRateList: Array<ProductRate>;
  productToSave: Product;
  bagProductList: Array<Product>;
  productPrices: ProductPricesWrapper;
  productQueryParams: ProductQueryParams;

  saveErrors: any;

  constructor(
    private productService: ProductService,
    private productRateService: ProductRateService
    ) {
    this.bagProductList = new Array();
    this.productQueryParams = new ProductQueryParams();
    this.productToSave = new Product();
  }

  ngOnInit() {
    this.getProductList();
    this.getProductRateList();
  }

  async getProductList(): Promise<void> {
    this.productList = await this.productService.findAll().toPromise();
  }

  async getProductRateList(): Promise<void> {
    this.productRateList = await this.productRateService.findAll().toPromise();
  }

  async saveProduct(): Promise<void> {
    try {
      await this.productService.save(this.productToSave).toPromise();
      this.getProductList();
    } catch (error) {
     this.saveErrors = error;
    }
  }

  async removeProduct(id: string): Promise<void> {
    await this.productService.remove(id).toPromise();
    this.getProductList();
  }

  bindRateToProduct(check: boolean, productRate: ProductRate): void {
    if (check) {
      this.productToSave.productRateList.push(productRate);
    } else {
      this.removeRateFromProduct(productRate.id);
    }
  }

  removeRateFromProduct(id: string) {
    this.productToSave.productRateList = this.productToSave.
    productRateList.filter(productRate => {
        return productRate.id !== id;
    });
  }

  addProductToBag(product: Product): void {

      const foundBagProduct = this.bagProductList.find(bagProduct => bagProduct.id === product.id);
      if (!foundBagProduct) {
        this.bagProductList.push(product);
      }
  }

  removeProductToBag(product: Product): void {
    this.bagProductList = this.bagProductList.filter(bagProduct => {
        return bagProduct.id !== product.id;
    });

    if (this.bagProductList && this.bagProductList.length === 0) {
      this.productPrices = undefined;
    }
  }

  async calculateTotalProductsPrices(): Promise<void> {

    if (this.bagProductList && this.bagProductList.length) {
      const productIds = this.bagProductList.map(product => product.id);
      this.productQueryParams.ids = productIds;
      this.productPrices = await this.productService.getProductPrices(this.productQueryParams).toPromise();
    } else {
      this.productPrices = undefined;
    }
  }

}
