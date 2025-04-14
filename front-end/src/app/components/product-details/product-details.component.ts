import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Product} from "../../common/product";
import {ActivatedRoute, Router} from "@angular/router";
import {CartService} from "../../services/cart.service";
import {CartItem} from "../../common/cart-item";

@Component({
    selector: 'app-product-details',
    templateUrl: './product-details.component.html',
    styleUrls: ['./product-details.component.css'],
    standalone: false
})
export class ProductDetailsComponent implements OnInit {

  product: Product = new Product();

  constructor(private products: ProductService,
              private cart: CartService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      const id = this.route.snapshot.paramMap.get('id');
      if (!id) {
        this.router.navigateByUrl("/products");
      } else {
        this.products.getProduct(+id).subscribe(product => this.product = product);
      }
    });
  }

  addToCart() {
    this.cart.addToCart(new CartItem(this.product));
  }
}
