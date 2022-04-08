import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Product} from "../common/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url: string = "http://localhost:8080/api/products";

  constructor(private http: HttpClient) { }

  listForCategory(categoryID: number): Observable<Product[]> {
    return this.http.get<GetResponse>(`${this.url}/search/findByCategoryId?id=${categoryID}`).pipe(
      map(response => response._embedded.products)
    );
  }
}

interface GetResponse {
  _embedded: {
    products: Product[];
  }
}
