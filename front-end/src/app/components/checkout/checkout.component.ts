import {Component, OnInit} from '@angular/core';
import {AbstractControl, UntypedFormBuilder, UntypedFormControl, UntypedFormGroup, Validators} from "@angular/forms";
import {Validators as ExValidators} from "../checkout/validators";
import {Country} from "../../common/country";
import {CountryService} from "../../services/country.service";
import {State} from "../../common/state";
import {CartService} from "../../services/cart.service";
import {Router} from "@angular/router";
import {OrderItem} from "../../common/order-item";
import {OrdersService} from "../../services/orders.service";

@Component({
    selector: 'app-checkout',
    templateUrl: './checkout.component.html',
    styleUrls: ['./checkout.component.css'],
    standalone: false
})
export class CheckoutComponent implements OnInit {

    totalQty: number = 0;
    totalPrice: number = 0;
    formGroup!: UntypedFormGroup;

    // country/states drop-down options
    countries: Country[] = [];
    shippingStates: State[] = [];

    storage: Storage = sessionStorage;

    // purchase button
    isEnabled: boolean = true;

    constructor(private readonly builder: UntypedFormBuilder,
                private readonly countryService: CountryService,
                private readonly cartService: CartService,
                private readonly ordersService: OrdersService,
                private readonly router: Router) {
    }

    ngOnInit(): void {
        const data = this.storage.getItem('customer');
        const customer = data ? JSON.parse(data) : undefined;

        this.formGroup = this.builder.group({
            customer: this.builder.group({
                firstName: new UntypedFormControl(customer?.firstName, [
                    Validators.required,
                    Validators.minLength(2),
                    ExValidators.blank
                ]),
                lastName: new UntypedFormControl(customer?.lastName, [
                    Validators.required,
                    Validators.minLength(2),
                    ExValidators.blank
                ]),
                email: new UntypedFormControl(customer?.email, [Validators.required,
                    Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])
            }),
            shippingAddress: this.builder.group({
                street: new UntypedFormControl('', [
                    Validators.required,
                    Validators.minLength(2),
                    ExValidators.blank
                ]),
                city: new UntypedFormControl('', [
                    Validators.required,
                    Validators.minLength(2),
                    ExValidators.blank
                ]),
                zip: new UntypedFormControl('', [
                    Validators.required,
                    Validators.minLength(2),
                    ExValidators.blank
                ]),
                state: new UntypedFormControl('', Validators.required),
                country: new UntypedFormControl('', Validators.required)
            })
        });
        this.cartService.count.subscribe(data => this.totalQty = data);
        this.cartService.amount.subscribe(data => this.totalPrice = data);
        // country/states drop-down options
        this.countryService.getCountries().subscribe(
            data => this.countries = data
        );
    }

    // form controls getters
    get firstName() {
        return this.formGroup.get('customer.firstName');
    }

    get lastName() {
        return this.formGroup.get('customer.lastName');
    }

    get email() {
        return this.formGroup.get('customer.email');
    }

    get shippingStreet() {
        return this.formGroup.get('shippingAddress.street');
    }

    get shippingCity() {
        return this.formGroup.get('shippingAddress.city');
    }

    get shippingState() {
        return this.formGroup.get('shippingAddress.state');
    }

    get shippingCountry() {
        return this.formGroup.get('shippingAddress.country');
    }

    get shippingZip() {
        return this.formGroup.get('shippingAddress.zip');
    }

    invalid(control: AbstractControl | null): boolean {
        if (control == null) return false;
        return control.invalid && (control.dirty || control.touched);
    }

    onSubmit() {
        if (this.formGroup.valid) {
            this.isEnabled = false;
            let items = this.cartService.items.map(item => new OrderItem(item));
            let shippingAddress = this.formGroup.controls['shippingAddress'].value;

            this.ordersService.placeOrder({shippingAddress: shippingAddress, items: items}).subscribe({
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
        } else {
            this.formGroup.markAllAsTouched();
        }
    }

    getStates() {
        let country = this.formGroup.get('shippingAddress')?.value.country;
        this.countryService.getStates(country.id).subscribe(
            data => {
                this.shippingStates = data;
                this.formGroup.get('shippingAddress.state')?.setValue(data[0]);
            }
        );
    }
}