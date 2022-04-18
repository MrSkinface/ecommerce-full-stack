import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Customer } from "../common/customer";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getCustomer(email?: string): Observable<Customer> {
    return this.http.get<Customer>(`${environment.apiBaseUrl}/customers/search/byEmail?email=${email}`);
  }
}
