import {CartItem} from "./cart-item";
import {Product} from "./product";

export class OrderItem {

  quantity: number;
  product: Product;


  constructor(cartItem: CartItem) {
    this.quantity = cartItem.quantity;
    this.product = cartItem.product;
  }
}
