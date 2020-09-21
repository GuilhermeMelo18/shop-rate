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
export class AppComponent {

  constructor() {}
}
