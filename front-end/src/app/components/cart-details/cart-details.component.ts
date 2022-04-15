import { Component, OnInit } from '@angular/core';
import {CartItem} from "../../common/cart-item";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'cart-details',
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.css']
})
export class CartDetailsComponent implements OnInit {

  items: CartItem[] = [];
  totalCount: number = 0;
  totalAmount: number = 0;

  constructor(private cart: CartService) { }

  ngOnInit(): void {
    this.items = this.cart.items;

    this.cart.count.subscribe(data => {
      this.totalCount = data;
    });

    this.cart.amount.subscribe(data => {
      this.totalAmount = data;
    });

    this.cart.calculateAndPublishTotals();
  }

  decrQty(item: CartItem) {
    this.cart.decrQty(item);
  }

  incrQty(item: CartItem) {
    this.cart.addToCart(item);
  }

  removeFromCart(item: CartItem) {
    this.cart.removeFromCart(item);
  }
}
