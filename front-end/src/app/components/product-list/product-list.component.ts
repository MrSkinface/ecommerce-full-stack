import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Product} from "../../common/product";
import {ActivatedRoute} from "@angular/router";
import {CartService} from "../../services/cart.service";
import {CartItem} from "../../common/cart-item";

@Component({
    selector: 'product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css'],
    standalone: false
})
export class ProductListComponent implements OnInit {

  private readonly _defaultPageSize: number = 10;

  get defaultPageSize(): number {
    return this._defaultPageSize;
  }

  list: Product[] = [];

  // pagination
  currentPage: number = 1;
  pageSize: number = this.defaultPageSize;
  totalSize: number = 0;

  constructor(private readonly products: ProductService,
              private readonly cart: CartService,
              private readonly route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleList();
    });
  }

  handleList() {
    const query = this.route.snapshot.paramMap.get('query');
    const id = this.route.snapshot.paramMap.get('id');
    if (query != null || id == null) {
      // search
      this.products.searchProducts(this.currentPage -1, this.pageSize, query)
          .subscribe(this.handleResult());
    } else {
      // list
      this.products.listProducts(this.currentPage -1, this.pageSize, +id)
          .subscribe(this.handleResult());
    }
  }

  private handleResult() {
    // @ts-ignore
    return data => {
      this.list = data.content;
      this.currentPage = data.number + 1;
      this.pageSize = data.size;
      this.totalSize = data.totalElements;
    }
  }

  changePageSize(event: Event) {
    this.pageSize = +(event.target as HTMLInputElement).value;
    this.currentPage = 1;
    this.handleList()
  }

  addToCart(product: Product) {
    console.log(`Add to cart: ${product.id} - ${product.name} - ${product.unitPrice}`);
    this.cart.addToCart(new CartItem(product))
  }
}
