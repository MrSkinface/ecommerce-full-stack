import { Injectable } from '@angular/core';
import {CartItem} from "../common/cart-item";
import {BehaviorSubject, ReplaySubject, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  items: CartItem[] = [];
  count: Subject<number> = new BehaviorSubject<number>(0);
  amount: Subject<number> = new BehaviorSubject<number>(0);

  constructor() { }

  addToCart(item: CartItem) {
    let existing: CartItem | undefined = this.items.find(el => el.product.id === item.product.id);
    if (existing) {
      existing.quantity++;
    } else {
      this.items.push(item);
    }
    this.calculateTotals();
  }

  decrQty(item: CartItem) {
    item.quantity--;
    if (item.quantity == 0) {
      this.removeFromCart(item);
    } else {
      this.calculateTotals();
    }
  }

  removeFromCart(item: CartItem) {
    this.items.forEach((el, index) => {
      if (el.product.id === item.product.id)
        this.items.splice(index, 1);
    })
    this.calculateTotals();
  }

  calculateTotals() {
    let count: number = 0;
    let amount: number = 0;
    for (const item of this.items) {
      count += item.quantity;
      amount += item.quantity * item.product.unitPrice;
    }
    // publish
    this.count.next(count);
    this.amount.next(amount);
  }
}
