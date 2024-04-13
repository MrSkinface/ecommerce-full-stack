import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../common/product";
import {Category} from "../common/category";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${environment.apiBaseUrl}/products/${id}`);
  }

  listProducts(page: number, size: number, categoryID: number): Observable<GetProductsResponse> {
    return this.http.get<GetProductsResponse>(`${environment.apiBaseUrl}/products?category_id=${categoryID}&page=${page}&size=${size}`)
  }

  searchProducts(page: number, size: number, query: string): Observable<GetProductsResponse> {
    return this.http.get<GetProductsResponse>(`${environment.apiBaseUrl}/products?name=${query}&page=${page}&size=${size}`)
  }

  categories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${environment.apiBaseUrl}/products/categories`);
  }
}

interface GetProductsResponse {
  content: Product[],
  number: number,
  size: number,
  totalElements: number
}
