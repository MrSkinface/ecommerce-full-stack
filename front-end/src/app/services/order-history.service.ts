import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OrderHistory} from "../common/order-history";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  private url = "http://localhost:8080/api/orders";

  constructor(private http: HttpClient) { }

  getOrdersHistory(customerID: number, page: number, size: number): Observable<GetOrdersHistoryResponse> {
    return this.http.get<GetOrdersHistoryResponse>(`${this.url}/search/findByCustomerId?id=${customerID}&page=${page}&size=${size}`)
  }

}

interface GetOrdersHistoryResponse {
  _embedded: {
    orders: OrderHistory[]
  },
  page: {
    number: number,
    size: number,
    totalElements: number
  }
}
