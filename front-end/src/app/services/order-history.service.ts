import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OrderHistory} from "../common/order-history";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  constructor(private http: HttpClient) { }

  getOrdersHistory(customerID: number, page: number, size: number): Observable<GetOrdersHistoryResponse> {
    return this.http.get<GetOrdersHistoryResponse>(`${environment.apiBaseUrl}/orders/search/findByCustomerId` +
    `?id=${customerID}&page=${page}&size=${size}&sort=dateCreated,desc`)
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
