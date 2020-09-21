import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../model/product';
import { ProductPricesWrapper } from '../model/product-prices-wrapper';
import { ProductQueryParams } from '../model/query-params/product-query-params';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private rootPath = 'product';
  constructor(private http: HttpClient) { }

  findAll(): Observable<Array<Product>> {
    return this.http.get<Array<Product>>(`${this.rootPath}`);
  }

  getProductPrices(queryParams: ProductQueryParams): Observable<ProductPricesWrapper> {
    return this.http.get<ProductPricesWrapper>(`${this.rootPath}/get-product-prices`, {params: {...queryParams}});
  }
}
