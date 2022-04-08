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

  list(): Observable<Product[]> {
    console.log("ProductService.list")
    return this.http.get<GetResponse>(this.url).pipe(
      map(response => response._embedded.products)
    );
  }
}

interface GetResponse {
  _embedded: {
    products: Product[];
  }
}
