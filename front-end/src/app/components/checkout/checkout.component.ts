import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Validators as ExValidators} from "../checkout/validators";
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
        firstName: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        lastName: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        email: new FormControl('', [Validators.required,
          Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])
      }),
      shippingAddress: this.builder.group({
        street: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        city: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        zip: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        state: new FormControl('', Validators.required),
        country: new FormControl('', Validators.required)
      }),
      billingAddress: this.builder.group({
        street: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        city: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        zip: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        state: new FormControl('', Validators.required),
        country: new FormControl('', Validators.required)
      }),
      creditCard: this.builder.group({
        type: new FormControl('', Validators.required),
        nameOnCard: new FormControl('', [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        number: new FormControl('', [Validators.required, Validators.pattern('[0-9]{16}')]),
        cvv: new FormControl('', [Validators.required, Validators.pattern('[0-9]{3}')]),
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

  // form controls getters
  get firstName() { return this.formGroup.get('customer.firstName'); }
  get lastName() { return this.formGroup.get('customer.lastName'); }
  get email() { return this.formGroup.get('customer.email'); }

  get shippingStreet() { return this.formGroup.get('shippingAddress.street'); }
  get shippingCity() { return this.formGroup.get('shippingAddress.city'); }
  get shippingState() { return this.formGroup.get('shippingAddress.state'); }
  get shippingCountry() { return this.formGroup.get('shippingAddress.country'); }
  get shippingZip() { return this.formGroup.get('shippingAddress.zip'); }

  get billingStreet() { return this.formGroup.get('billingAddress.street'); }
  get billingCity() { return this.formGroup.get('billingAddress.city'); }
  get billingState() { return this.formGroup.get('billingAddress.state'); }
  get billingCountry() { return this.formGroup.get('billingAddress.country'); }
  get billingZip() { return this.formGroup.get('billingAddress.zip'); }

  get ccType() { return this.formGroup.get('creditCard.type'); }
  get ccName() { return this.formGroup.get('creditCard.nameOnCard'); }
  get ccNumber() { return this.formGroup.get('creditCard.number'); }
  get ccCvv() { return this.formGroup.get('creditCard.cvv'); }
  get ccExpMonth() { return this.formGroup.get('creditCard.expirationMonth'); }
  get ccExpYear() { return this.formGroup.get('creditCard.expirationYear'); }

  invalid(control: AbstractControl | null): boolean {
    if (control == null) return false;
    return control.invalid && (control.dirty || control.touched);
  }

  onSubmit() {
    console.log('Handling submit form');
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
    }
    console.log(this.formGroup.get('customer')?.value);
    console.log(this.shippingStreet?.errors)
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
        this.formGroup.get(`${formGroupName}.state`)?.setValue(data[0]);
      }
    );
  }
}
