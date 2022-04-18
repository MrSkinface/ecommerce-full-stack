import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Validators as ExValidators} from "../checkout/validators";
import {Observable, of} from "rxjs";
import {Country} from "../../common/country";
import {CountryService} from "../../services/country.service";
import {State} from "../../common/state";
import {CartService} from "../../services/cart.service";
import {CheckoutService} from "../../services/checkout.service";
import {Router} from "@angular/router";
import {Order} from "../../common/order";
import {OrderItem} from "../../common/order-item";
import {Purchase} from "../../common/purchase";

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

  storage: Storage = sessionStorage;

  constructor(private builder: FormBuilder,
              private countryService: CountryService,
              private cart: CartService,
              private checkout: CheckoutService,
              private router: Router) { }

  ngOnInit(): void {
    const data = this.storage.getItem('customer');
    const customer = data ? JSON.parse(data) : undefined;

    this.formGroup = this.builder.group({
      customer: this.builder.group({
        firstName: new FormControl(customer?.firstName, [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        lastName: new FormControl(customer?.lastName, [
          Validators.required,
          Validators.minLength(2),
          ExValidators.blank
        ]),
        email: new FormControl(customer?.email, [Validators.required,
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
    this.cart.count.subscribe(data => this.totalQty = data);
    this.cart.amount.subscribe(data => this.totalPrice = data);
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

  invalid(control: AbstractControl | null): boolean {
    if (control == null) return false;
    return control.invalid && (control.dirty || control.touched);
  }

  onSubmit() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

    // order
    let order = new Order(this.totalQty, this.totalPrice);
    // items
    let items = this.cart.items.map(item => new OrderItem(item));
    // customer
    let customer = this.formGroup.controls['customer'].value;
    // shipping address
    let shippingAddress = this.formGroup.controls['shippingAddress'].value;
    // billing address
    let billingAddress = this.formGroup.controls['billingAddress'].value;
    // purchase
    let purchase = new Purchase(customer, shippingAddress, billingAddress, order, items);

    this.checkout.placeOrder(purchase).subscribe({
      next: response => {
        alert(`Order have been successfully saved./n Tracking UUID: ${response.trackingUUID}`);
        this.cart.resetCart();
        this.formGroup.reset();
        this.router.navigateByUrl('/products');
      },
      error: err => {
        alert(`ERROR: ${err.message}`);
      }
    });
  }



  copyAddressShippingToBilling(event: Event) {
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
