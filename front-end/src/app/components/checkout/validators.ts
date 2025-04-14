import { UntypedFormControl, ValidationErrors } from "@angular/forms";

export class Validators {

  static blank(control: UntypedFormControl): ValidationErrors {
    if (control.value != null && control.value.trim().length == 0)
      return { 'blank': true }
    return {};
  }
}
