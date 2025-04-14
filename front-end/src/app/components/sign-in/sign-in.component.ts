import {Component, OnInit} from '@angular/core';
import {SocialAuthService, SocialUser} from "@abacritt/angularx-social-login";

@Component({
    selector: 'app-sign-in',
    templateUrl: './sign-in.component.html',
    styleUrl: './sign-in.component.css',
    standalone: false
})
export class SignInComponent implements OnInit {

    user!: SocialUser;
    isLoggedIn!: boolean;


    constructor(private readonly authService: SocialAuthService) {
    }

    ngOnInit(): void {
        this.authService.authState.subscribe((user) => {
            this.user = user;
            this.isLoggedIn = !!user;
            console.table(user);
            this.handleAuthentication();
        });
    }

    signOut(): void {
        this.authService.signOut();
    }

    private handleAuthentication() {
        if (this.isLoggedIn) {
            // todo
            console.info("Api call to sync auth")
        }
    }
}