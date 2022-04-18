import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Customer } from "../common/customer";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private url = "http://localhost:8080/api/customers";

  constructor(private http: HttpClient) { }

  getCustomer(email?: string): Observable<Customer> {
    return this.http.get<Customer>(`${this.url}/search/byEmail?email=${email}`);
  }
}
