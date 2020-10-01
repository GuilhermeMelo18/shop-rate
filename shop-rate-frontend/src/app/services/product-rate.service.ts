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
export class ProductRateService {

  private rootPath = 'productRate';
  constructor(private http: HttpClient) { }

  findAll(): Observable<Array<ProductRate>> {
    return this.http.get<Array<ProductRate>>(`${this.rootPath}`);
  }

}
