import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs";
import {AuthService} from "../services/auth.service";
import {Injectable} from "@angular/core";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private readonly authService: AuthService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.authService.getAuthData()) {
            request = request.clone({
                setHeaders: {
                    // @ts-ignore
                    'x-auth-token': this.authService.getAuthData().authToken
                }
            });
        }
        return next.handle(request);
    }
}