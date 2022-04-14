import { FormControl, ValidationErrors } from "@angular/forms";

export class Validators {

  static blank(control: FormControl): ValidationErrors {
    if (control.value != null && control.value.trim().length == 0)
      return { 'blank': true }
    return {};
  }
}
