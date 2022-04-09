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

  listForCategory(categoryID: number): Observable<Product[]> {
    return this.getProducts(`${this.url}/products/search/findByCategoryId?id=${categoryID}`)
  }

  search(query: string): Observable<Product[]> {
    return this.getProducts(`${this.url}/products/search/findByNameContaining?name=${query}`)
  }

  getProducts(url: string): Observable<Product[]> {
    return this.http.get<GetProductsResponse>(url).pipe(
      map(response => response._embedded.products)
    );
  }

  categories(): Observable<Category[]> {
    return this.http.get<GetCategoriesResponse>(`${this.url}/product-category`).pipe(
      map(response => response._embedded.categories)
    );
  }
}

interface GetProductsResponse {
  _embedded: {
    products: Product[];
  }
}

interface GetCategoriesResponse {
  _embedded: {
    categories: Category[];
  }
}
