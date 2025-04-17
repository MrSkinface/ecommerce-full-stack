
import {OrderItem} from "./order-item";

export class Order {
  id!: number;
  trackingNumber!: string;
  totalPrice!: number;
  totalQuantity!: number;
  dateCreated!: Date;
  items: OrderItem[] = [];
}