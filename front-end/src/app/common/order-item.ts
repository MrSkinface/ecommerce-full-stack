import {CartItem} from "./cart-item";

export class OrderItem {

  quantity: number;
  productId: number;

  constructor(cartItem: CartItem) {
    this.quantity = cartItem.quantity;
    this.productId = cartItem.product.id;
  }
}