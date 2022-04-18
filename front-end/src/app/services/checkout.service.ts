import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Purchase } from "../common/purchase";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  constructor(private http: HttpClient) { }

  placeOrder(purchase: Purchase): Observable<any> {
    return this.http.post<Purchase>(`${environment.apiBaseUrl}/api/checkout/purchase`, purchase);
  }
}
