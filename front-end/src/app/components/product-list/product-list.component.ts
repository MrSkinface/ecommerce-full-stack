import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Product} from "../../common/product";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  list: Product[] = [];
  previousCategory: number = 1;
  currentCategory: number = 1;

  // pagination
  currentPage: number = 1;
  pageSize: number = 10;
  totalSize: number = 0;

  constructor(private products: ProductService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleList();
    });
  }

  handleList() {
    const query = this.route.snapshot.paramMap.get('query');
    const id = this.route.snapshot.paramMap.get('id');
    if (query != null) {
      // search
      this.products.searchProducts(this.currentPage -1, this.pageSize, query).subscribe(this.handleResult());
    } else {
      // list
      if (id != null) this.currentCategory = +id;

      if (this.previousCategory != this.currentCategory) {
        this.currentPage = 1;
      }
      this.previousCategory = this.currentCategory;

      this.products.listProducts(this.currentPage -1, this.pageSize, this.currentCategory).subscribe(this.handleResult());
    }
  }

  private handleResult() {
    // @ts-ignore
    return data => {
      this.list = data._embedded.products;
      this.currentPage = data.page.number + 1;
      this.pageSize = data.page.size;
      this.totalSize = data.page.totalElements;
    }
  }
}
