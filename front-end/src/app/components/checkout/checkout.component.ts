import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Validators as ExValidators} from "../checkout/validators";
import {Country} from "../../common/country";
import {CountryService} from "../../services/country.service";
import {State} from "../../common/state";
import {CartService} from "../../services/cart.service";
import {CheckoutService} from "../../services/checkout.service";
import {Router} from "@angular/router";
import {Order} from "../../common/order";
import {OrderItem} from "../../common/order-item";
import {Purchase} from "../../common/purchase";
import {PaymentInfo} from "../../common/payment-info";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  paymentInfo: PaymentInfo = new PaymentInfo();
  cardElement: any;
  displayError: any = '';

  totalQty: number = 0;
  totalPrice: number = 0;
  formGroup!: FormGroup;

  // country/states drop-down options
  countries: Country[] = [];
  shippingStates: State[] = [];
  billingStates: State[] = [];

  storage: Storage = sessionStorage;

  // purchase button
  isEnabled: boolean = true;

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
      creditCard: this.builder.group({})
    });
    this.cart.count.subscribe(data => this.totalQty = data);
    this.cart.amount.subscribe(data => this.totalPrice = data);
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

  invalid(control: AbstractControl | null): boolean {
    if (control == null) return false;
    return control.invalid && (control.dirty || control.touched);
  }

  onSubmit() {
    if (!this.formGroup.invalid && this.displayError.textContent === "") {
      this.isEnabled = false;
      let order = new Order(this.totalQty, this.totalPrice);
      let items = this.cart.items.map(item => new OrderItem(item));
      let customer = this.formGroup.controls['customer'].value;
      let shippingAddress = this.formGroup.controls['shippingAddress'].value;
      let billingAddress = this.formGroup.controls['billingAddress'].value;
      let purchase = new Purchase(customer, shippingAddress, billingAddress, order, items);

      this.paymentInfo.amount = Math.round(this.totalPrice * 100);
      this.paymentInfo.currency = 'USD';
      this.paymentInfo.receiptEmail = customer.email;

      this.checkout.createPaymentIntent(this.paymentInfo).subscribe(
        (paymentIntentResponse) => {
          this.checkout.placeOrder(purchase).subscribe({
            next: (response: { trackingUUID: any; }) => {
              alert(`Your order has been received.\nOrder tracking number: ${response.trackingUUID}`);
              // reset cart
              // @ts-ignore
              this.cart.resetCart();
              // @ts-ignore
              this.formGroup.reset();
              // @ts-ignore
              this.router.navigateByUrl('/products');
              // @ts-ignore
              this.isEnabled = true;
            },
            error: (err: { message: any; }) => {
              alert(`There was an error: ${err.message}`);
              // @ts-ignore
              this.isEnabled = true;
            }
          })
        }
      );
    } else {
      this.formGroup.markAllAsTouched();
      return;
    }
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
