import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../model/product';
import { ProductPricesWrapper } from '../model/product-prices-wrapper';
import { ProductRate } from '../model/product-rate';
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

  save(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.rootPath}`, product);
  }

  getProductPrices(queryParams: ProductQueryParams): Observable<ProductPricesWrapper> {
    return this.http.get<ProductPricesWrapper>(`${this.rootPath}/get-product-prices`, {params: {...queryParams}});
  }
  remove(id: string): Observable<ProductPricesWrapper> {
    return this.http.delete<ProductPricesWrapper>(`${this.rootPath}/${id}`);
  }
}
