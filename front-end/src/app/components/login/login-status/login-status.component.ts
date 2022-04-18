import {Component, Inject, OnInit} from '@angular/core';
import {AuthState, OktaAuth} from "@okta/okta-auth-js";
import {OKTA_AUTH, OktaAuthStateService} from "@okta/okta-angular";
import {filter, map, Observable, of} from "rxjs";
import {Router} from "@angular/router";
import {CustomerService} from "../../../services/customer.service";
import {Customer} from "../../../common/customer";

@Component({
  selector: 'login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {

  isAuthenticated$!: Observable<boolean>;
  customer?: Customer;

  storage: Storage = sessionStorage;

  constructor(private customerService: CustomerService,
              private router: Router,
              private oktaStateService: OktaAuthStateService,
              @Inject(OKTA_AUTH) private oktaAuth: OktaAuth) { }

  async ngOnInit() {
    this.isAuthenticated$ = this.oktaStateService.authState$.pipe(
      filter((s: AuthState) => !!s),
      map((s: AuthState) => {
        const isAuthenticated = s.isAuthenticated;
        if (isAuthenticated) {
          this.customerService.getCustomer(s.idToken?.claims?.email).subscribe(data => {
            this.customer = data;
            this.storage.setItem('customer', JSON.stringify(this.customer));
          });
        } else {
          this.storage.removeItem('customer');
        }
        return isAuthenticated ?? false
      })
    );
  }

  async logout(): Promise<void> {
    await this.oktaAuth.signOut();
  }

}
