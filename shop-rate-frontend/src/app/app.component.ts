import { Component, OnInit } from '@angular/core';
import { Product } from './model/product';
import { ProductPricesWrapper } from './model/product-prices-wrapper';
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

  constructor(private productService: ProductService) {
    this.bagProductList = new Array();
  }

  ngOnInit() {
    this.getProductList();
  }

  async getProductList(): Promise<void> {
    this.productList = await this.productService.findAll().toPromise();
  }
  addProductToBag(product: Product): void {
      this.bagProductList.push(product);
  }

  removeProductToBag(product: Product): void {
    this.bagProductList = this.bagProductList.filter(bagProduct => {
        return bagProduct.id !== product.id;
    });
  }

  calculateTotalProductsPrices(): void {
    const productIds = this.bagProductList.map(product => product.id);
  }

}
