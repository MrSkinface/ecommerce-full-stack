import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Country} from "../common/country";
import {State} from "../common/state";

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private url: string = "http://localhost:8080/api";

  constructor(private http: HttpClient) { }

  getCountries(): Observable<Country[]> {
    return this.http.get<GetCountriesResponse>(`${this.url}/countries`).pipe(
      map(data => data._embedded.countries)
    );
  }

  getStates(countryID: number): Observable<State[]> {
    return this.http.get<GetStatesResponse>(`${this.url}/states/search/findByCountryId?id=${countryID}`).pipe(
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
