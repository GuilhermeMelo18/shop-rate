import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './model/product';
import { ProductPricesWrapper } from './model/product-prices-wrapper';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private rootPath = 'product';
  constructor(private http: HttpClient) { }

  findAll(): Observable<Array<Product>> {
    return this.http.get<Array<Product>>(`${this.rootPath}`);
  }

  getProductPrices(): Observable<ProductPricesWrapper> {
    return this.http.get<ProductPricesWrapper>(`${this.rootPath}/get-product-prices`);
  }
}
