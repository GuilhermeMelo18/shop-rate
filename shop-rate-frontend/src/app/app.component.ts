import { Component, OnInit } from '@angular/core';
import { Product } from './model/product';
import { ProductPricesWrapper } from './model/product-prices-wrapper';
import { ProductQueryParams } from './model/query-params/product-query-params';
import { ProductService } from './services/product.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  productList: Array<Product>;

  bagProductList: Array<Product>;
  productPrices: ProductPricesWrapper;

  productQueryParams: ProductQueryParams;

  constructor(private productService: ProductService) {
    this.bagProductList = new Array();
    this.productQueryParams = new ProductQueryParams();
  }

  ngOnInit() {
    this.getProductList();
  }

  async getProductList(): Promise<void> {
    this.productList = await this.productService.findAll().toPromise();
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
