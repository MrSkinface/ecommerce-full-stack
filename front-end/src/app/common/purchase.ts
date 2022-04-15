import {Customer} from "./customer";
import {Address} from "./address";
import {Order} from "./order";
import {OrderItem} from "./order-item";

export class Purchase {

  customer: Customer;
  shippingAddress: Address;
  billingAddress: Address;
  order: Order;
  items: OrderItem[];


  constructor(customer: Customer, shippingAddress: Address, billingAddress: Address, order: Order, items: OrderItem[]) {
    this.customer = customer;
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
    this.order = order;
    this.items = items;
  }
}
