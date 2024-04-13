import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Country} from "../common/country";
import {State} from "../common/state";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  constructor(private http: HttpClient) { }

  getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(`${environment.apiBaseUrl}/countries`);
  }

  getStates(countryID: number): Observable<State[]> {
    return this.http.get<State[]>(`${environment.apiBaseUrl}/countries/${countryID}/states`);
  }

}
