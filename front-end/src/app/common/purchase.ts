import {Address} from "./address";
import {PurchaseItem} from "./purchase-item";

export class Purchase {

  shippingAddress!: Address;
  items!: PurchaseItem[];
}