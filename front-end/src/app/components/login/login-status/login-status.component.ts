import {Component, Inject, OnInit} from '@angular/core';
import {AuthState, OktaAuth} from "@okta/okta-auth-js";
import {OKTA_AUTH, OktaAuthStateService} from "@okta/okta-angular";
import {filter, map, Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {

  isAuthenticated$!: Observable<boolean>;
  name$!: Observable<string>;

  constructor(private router: Router, private oktaStateService: OktaAuthStateService, @Inject(OKTA_AUTH) private oktaAuth: OktaAuth) { }

  async ngOnInit() {
    this.isAuthenticated$ = this.oktaStateService.authState$.pipe(
      filter((s: AuthState) => !!s),
      map((s: AuthState) => s.isAuthenticated ?? false)
    );
    this.name$ = this.oktaStateService.authState$.pipe(
      filter((s: AuthState) => !!s),
      map((authState: AuthState) => authState.idToken?.claims.name ?? '')
    );
  }

  async logout(): Promise<void> {
    await this.oktaAuth.signOut();
  }

}
