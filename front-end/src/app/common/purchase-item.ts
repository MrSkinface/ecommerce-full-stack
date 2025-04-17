import {CartItem} from "./cart-item";

export class PurchaseItem {

  quantity: number;
  productId: number;

  constructor(cartItem: CartItem) {
    this.quantity = cartItem.quantity;
    this.productId = cartItem.product.id;
  }
}