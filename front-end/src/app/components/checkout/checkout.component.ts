import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  formGroup!: FormGroup;

  constructor(private builder: FormBuilder) { }

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
  }

  onSubmit() {
    console.log('Handling submit form');
    console.log(this.formGroup.get('customer')?.value);
  }

  copyAdressShippingToBilling(event: Event) {
    if ((event.target as HTMLInputElement).checked) {
      this.formGroup.controls['billingAddress'].setValue(this.formGroup.controls['shippingAddress'].value);
    } else {
      this.formGroup.controls['billingAddress'].reset();
    }
  }
}
