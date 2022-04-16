import {Component, Inject, OnInit} from '@angular/core';
import { OktaAuth, Tokens } from '@okta/okta-auth-js';
// @ts-ignore
import * as OktaSignIn from '@okta/okta-signin-widget';
import appConfig from "../../config/app-config";
import {OKTA_AUTH} from "@okta/okta-angular";
import {Router} from "@angular/router";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  oktaSignin: any;

  constructor(private router: Router, @Inject(OKTA_AUTH) private oktaAuth: OktaAuth) {
    this.oktaSignin = new OktaSignIn({
      logo: 'assets/images/logo.png',
      baseUrl: appConfig.oidc.issuer.split('/oauth2')[0],
      clientId: appConfig.oidc.clientId,
      redirectUri: appConfig.oidc.redirectUri,
      authParams: {
        pkce: true,
        issuer: appConfig.oidc.issuer,
        scopes: appConfig.oidc.scopes
      }
    });
  }

  ngOnInit(): void {
    this.oktaSignin.remove();
    this.oktaSignin.showSignInToGetTokens({
      el: '#okta-sign-in-widget',
      scopes: appConfig.oidc.scopes
    }).then((tokens: Tokens) => {
      // Remove the widget
      this.oktaSignin.remove();
      // In this flow the redirect to Okta occurs in a hidden iframe
      this.oktaAuth.handleLoginRedirect(tokens)
    }).catch((err: any) => {
      // Typically due to misconfiguration
      throw err;
    });
  }

}
