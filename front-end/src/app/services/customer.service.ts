import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Customer } from "../common/customer";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private readonly http: HttpClient) { }

  authorizeCustomer(token: string): Observable<Customer> {
    return this.http.post<Customer>(`${environment.apiBaseUrl}/customers/authorize`, null, {headers: {'x-auth-token': token}});
  }
}
