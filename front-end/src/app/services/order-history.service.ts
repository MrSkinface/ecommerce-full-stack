import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OrderHistory} from "../common/order-history";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class OrderHistoryService {

    constructor(private readonly http: HttpClient) {
    }

    getOrdersHistory(page: number, size: number): Observable<GetOrdersHistoryResponse> {
        return this.http.get<GetOrdersHistoryResponse>(`${environment.apiBaseUrl}/orders?page=${page}&size=${size}&sort=dateCreated,desc`)
    }
}

interface GetOrdersHistoryResponse {
    content: OrderHistory[],
    number: number,
    size: number,
    totalElements: number
}