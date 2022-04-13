import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Observable, of} from "rxjs";
import {Country} from "../../common/country";
import {CountryService} from "../../services/country.service";
import {State} from "../../common/state";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  totalQty: number = 0;
  totalPrice: number = 0;
  formGroup!: FormGroup;

  // credit card drop-down options
  ccMonths: number[] = [];
  ccYears: number[] = [];
  // country/states drop-down options
  countries: Country[] = [];
  shippingStates: State[] = [];
  billingStates: State[] = [];

  constructor(private builder: FormBuilder,
              private countryService: CountryService) { }

  ngOnInit(): void {
    this.formGroup = this.builder.group({
      customer: this.builder.group({
        firstName: [''],
        lastName: [''],
        email: ['']
      }),
      shippingAddress: this.builder.group({
        street: [''],
        city: [''],
        state: [''],
        country: [''],
        zip: ['']
      }),
      billingAddress: this.builder.group({
        street: [''],
        city: [''],
        state: [''],
        country: [''],
        zip: ['']
      }),
      creditCard: this.builder.group({
        type: [''],
        nameOnCard: [''],
        number: [''],
        cvv: [''],
        expirationMonth: [''],
        expirationYear: ['']
      })
    });
    // credit card drop-down options
    CheckoutComponent.getCCMonths().subscribe(data => this.ccMonths = data);
    CheckoutComponent.getCCYears().subscribe(data => this.ccYears = data);
    // country/states drop-down options
    this.countryService.getCountries().subscribe(
      data => this.countries = data
    );
  }

  onSubmit() {
    console.log('Handling submit form');
    console.log(this.formGroup.get('customer')?.value);
  }



  copyAdressShippingToBilling(event: Event) {
    if ((event.target as HTMLInputElement).checked) {
      this.formGroup.controls['billingAddress'].setValue(this.formGroup.controls['shippingAddress'].value);
      this.billingStates = this.shippingStates;
    } else {
      this.formGroup.controls['billingAddress'].reset();
      this.billingStates = [];
    }
  }

  handleCCYearChange() {
    let selectedYear = +this.formGroup.get('creditCard')?.value.expirationYear;
    let currYear = new Date().getFullYear();
    let startMonthFrom = undefined;
    if (selectedYear != currYear) startMonthFrom = 1;
    CheckoutComponent.getCCMonths(startMonthFrom).subscribe(data => this.ccMonths = data);
  }

  private static getCCMonths(startMonth?: number): Observable<number[]> {
    let months: number[] = [];
    for (let i = startMonth ? startMonth : new Date().getMonth() + 1; i <= 12; i++) {
      months.push(i);
    }
    return of(months);
  }

  private static getCCYears(): Observable<number[]> {
    let years: number[] = [];
    let currentYear = new Date().getFullYear();
    for (let i = 0; i < 10; i++) {
      years.push(currentYear + i);
    }
    return of(years);
  }

  getStates(formGroupName: string) {
    let country = this.formGroup.get(formGroupName)?.value.country;
    this.countryService.getStates(country.id).subscribe(
      data => {
        if (formGroupName === 'shippingAddress') {
          this.shippingStates = data;
        } else {
          this.billingStates = data;
        }
        this.formGroup.get(formGroupName)?.get('state')?.setValue(data[0]);
      }
    );
  }
}
