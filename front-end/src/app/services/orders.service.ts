import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OrderHistory} from "../common/order-history";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Purchase} from "../common/purchase";
import {Order} from "../common/order";

@Injectable({
    providedIn: 'root'
})
export class OrdersService {

    constructor(private readonly http: HttpClient) {
    }

    placeOrder(purchase: Purchase): Observable<any> {
        return this.http.post<Purchase>(`${environment.apiBaseUrl}/orders`, purchase);
    }

    getOrdersHistory(page: number, size: number): Observable<GetOrdersHistoryResponse> {
        return this.http.get<GetOrdersHistoryResponse>(`${environment.apiBaseUrl}/orders?page=${page}&size=${size}&sort=dateCreated,desc`)
    }

    getOrderDetails(id: number): Observable<Order> {
        return this.http.get<Order>(`${environment.apiBaseUrl}/orders/${id}`);
    }
}

interface GetOrdersHistoryResponse {
    content: OrderHistory[],
    number: number,
    size: number,
    totalElements: number
}