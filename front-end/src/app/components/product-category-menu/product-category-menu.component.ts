import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Category} from "../../common/category";

@Component({
    selector: 'product-category-menu',
    templateUrl: './product-category-menu.component.html',
    styleUrls: ['./product-category-menu.component.css'],
    standalone: false
})
export class ProductCategoryMenuComponent implements OnInit {

  categories: Category[] = [];
  constructor(private products: ProductService) { }

  ngOnInit(): void {
    this.products.categories().subscribe(data => this.categories = data)
  }

}
