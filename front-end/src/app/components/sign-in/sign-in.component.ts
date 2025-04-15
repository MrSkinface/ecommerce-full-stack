import {Component, OnInit} from '@angular/core';
import {SocialAuthService} from "@abacritt/angularx-social-login";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-sign-in',
    templateUrl: './sign-in.component.html',
    styleUrl: './sign-in.component.css',
    standalone: false
})
export class SignInComponent implements OnInit {

    constructor(private readonly socialAuthService: SocialAuthService,
                private readonly authService: AuthService,
                private readonly router: Router) {
    }

    isLoggedIn!: boolean;
    userEmail!: string | undefined;

    ngOnInit(): void {
        this.socialAuthService.authState.subscribe((user) => {
            if (user) {
                this.authService.authenticate(user.idToken).subscribe(data => {
                    this.authService.setAuthData(user.idToken, data);
                    this.isLoggedIn = !!data;
                    this.userEmail = data.email;
                    this.router.navigateByUrl('/');
                });
            }
        });
    }


    signOut(): void {
        this.socialAuthService.signOut();
        this.authService.removeAuthData();
        this.isLoggedIn = false;
        this.userEmail = undefined;
    }
}