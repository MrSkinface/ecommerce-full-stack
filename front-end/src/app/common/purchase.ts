import {Address} from "./address";
import {OrderItem} from "./order-item";

export class Purchase {

  shippingAddress!: Address;
  items!: OrderItem[];
}