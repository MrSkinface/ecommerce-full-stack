import {Injectable} from '@angular/core';
import {CustomerService} from "./customer.service";
import {Customer} from "../common/customer";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private readonly AUTH_SESS_KEY: string = 'AUTH_KEY';

    auth: AuthData | undefined;

    storage: Storage = sessionStorage;

    constructor(private readonly customerService: CustomerService) {
    }

    authenticate(authToken: string): Observable<Customer> {
        return this.customerService.authorizeCustomer(authToken);
    }

    setAuthData(authToken: string, customer: Customer) {
        this.auth = {authToken: authToken, customer: customer};
        this.storage.setItem(this.AUTH_SESS_KEY, JSON.stringify(this.auth));
    }

    getAuthData(): AuthData | undefined {
        if (!this.auth) {
            this.auth = this.jsonParse<AuthData>(this.storage.getItem(this.AUTH_SESS_KEY));
        }
        return this.auth;
    }

    removeAuthData() {
        this.auth = undefined;
        this.storage.removeItem(this.AUTH_SESS_KEY);
    }

    private readonly jsonParse = <T>(str: string | null) => {
        try {
            if (str === null) return undefined;
            const jsonValue: T = JSON.parse(str);
            return jsonValue;
        } catch {
            return undefined;
        }
    };
}

interface AuthData {
    authToken: string,
    customer: Customer
}