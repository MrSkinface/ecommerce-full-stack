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
  currentCategory: number = 1;

  constructor(private products: ProductService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      const id = this.route.snapshot.paramMap.get('id');
      if (id != null) this.currentCategory = +id;
      this.products.listForCategory(this.currentCategory).subscribe(data => this.list = data);
    });
  }

}
