import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Country} from "../common/country";
import {State} from "../common/state";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  constructor(private http: HttpClient) { }

  getCountries(): Observable<Country[]> {
    return this.http.get<GetCountriesResponse>(`${environment.apiBaseUrl}/countries`).pipe(
      map(data => data._embedded.countries)
    );
  }

  getStates(countryID: number): Observable<State[]> {
    return this.http.get<GetStatesResponse>(`${environment.apiBaseUrl}/states/search/findByCountryId?id=${countryID}`).pipe(
      map(data => data._embedded.states)
    );
  }

}

interface GetCountriesResponse {
  _embedded: {
    countries: Country[];
  }
}

interface GetStatesResponse {
  _embedded: {
    states: State[];
  }
}
