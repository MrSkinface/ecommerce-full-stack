import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {ProductListComponent} from './components/product-list/product-list.component';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {ProductService} from "./services/product.service";
import {RouterModule, Routes} from "@angular/router";
import {ProductCategoryMenuComponent} from './components/product-category-menu/product-category-menu.component';
import {SearchComponent} from './components/search/search.component';
import {ProductDetailsComponent} from './components/product-details/product-details.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CartStatusComponent} from './components/cart-status/cart-status.component';
import {CartDetailsComponent} from './components/cart-details/cart-details.component';
import {CheckoutComponent} from './components/checkout/checkout.component';
import {ReactiveFormsModule} from "@angular/forms";

import {OrderHistoryComponent} from './components/order-history/order-history.component';
import {GoogleSigninButtonModule, SocialLoginModule} from "@abacritt/angularx-social-login";
import {authConfig} from "./auth-config";
import {SignInComponent} from "./components/sign-in/sign-in.component";
import {NgOptimizedImage} from "@angular/common";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {OrderDetailsComponent} from "./components/order-details/order-details.component";

const routes: Routes = [
    {path: 'orders-history', component: OrderHistoryComponent},
    {path: 'search/:query', component: ProductListComponent},
    {path: 'orders/:id', component: OrderDetailsComponent},
    {path: 'category/:id', component: ProductListComponent},
    {path: 'category', component: ProductListComponent},
    {path: 'products', component: ProductListComponent},
    {path: 'products/:id', component: ProductDetailsComponent},
    {path: 'cart', component: CartDetailsComponent},
    {path: 'checkout', component: CheckoutComponent},
    {path: '', redirectTo: '/products', pathMatch: 'full'},
    {path: '**', redirectTo: '/products', pathMatch: 'full'}
];

@NgModule({
    declarations: [
        AppComponent,
        ProductListComponent,
        ProductCategoryMenuComponent,
        SearchComponent,
        ProductDetailsComponent,
        CartStatusComponent,
        CartDetailsComponent,
        CheckoutComponent,
        OrderHistoryComponent,
        SignInComponent
    ],
    bootstrap: [AppComponent], imports: [RouterModule.forRoot(routes),
        BrowserModule,
        ReactiveFormsModule,
        NgbModule,
        SocialLoginModule, GoogleSigninButtonModule, NgOptimizedImage], providers: [
        ProductService,
        {
            provide: 'SocialAuthServiceConfig',
            useValue: authConfig,
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        provideHttpClient(withInterceptorsFromDi())
    ]
})
export class AppModule {
}
