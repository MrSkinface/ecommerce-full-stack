import {Country} from "./country";
import {State} from "./state";

export class Address {

  country!: Country;
  state!: State;
  city!: string;
  street!: string;
  zip!: string;
}
