import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Product} from "../common/product";
import {Category} from "../common/category";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url: string = "http://localhost:8080/api";

  constructor(private http: HttpClient) { }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.url}/products/${id}`);
  }

  listProducts(page: number, size: number, categoryID: number): Observable<GetProductsResponse> {
    return this.http.get<GetProductsResponse>(`${this.url}/products/search/findByCategoryId?id=${categoryID}&page=${page}&size=${size}`)
  }

  searchProducts(page: number, size: number, query: string): Observable<GetProductsResponse> {
    return this.http.get<GetProductsResponse>(`${this.url}/products/search/findByNameContaining?name=${query}&page=${page}&size=${size}`)
  }

  categories(): Observable<Category[]> {
    return this.http.get<GetCategoriesResponse>(`${this.url}/product-category`).pipe(
      map(response => response._embedded.categories)
    );
  }
}

interface GetProductsResponse {
  _embedded: {
    products: Product[]
  },
  page: {
    number: number,
    size: number,
    totalElements: number
  }
}

interface GetCategoriesResponse {
  _embedded: {
    categories: Category[]
  }
}
