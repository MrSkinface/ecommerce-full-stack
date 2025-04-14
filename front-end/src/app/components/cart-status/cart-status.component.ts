import { Component, OnInit } from '@angular/core';
import {CartService} from "../../services/cart.service";

@Component({
    selector: 'cart-status',
    templateUrl: './cart-status.component.html',
    styleUrls: ['./cart-status.component.css'],
    standalone: false
})
export class CartStatusComponent implements OnInit {

  count: number = 0;
  amount: number = 0;

  constructor(private cart: CartService) { }

  ngOnInit(): void {
    this.cart.count.subscribe(data => this.count = data);
    this.cart.amount.subscribe(data => this.amount = data);
  }

}
