import {Component, OnInit} from '@angular/core';
import {Order} from "../../common/order";
import {OrdersService} from "../../services/orders.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {CurrencyPipe, NgForOf} from "@angular/common";

@Component({
  selector: 'app-order-details',
  imports: [
    CurrencyPipe,
    NgForOf
  ],
  templateUrl: './order-details.component.html',
  styleUrl: './order-details.component.css'
})
export class OrderDetailsComponent implements OnInit {

  order: Order = new Order();

  constructor(private readonly orderService: OrdersService,
              private readonly route: ActivatedRoute,
              private readonly router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      const id = this.route.snapshot.paramMap.get('id');
      if (!id) {
        this.router.navigateByUrl('/orders');
      } else {
        this.orderService.getOrderDetails(+id).subscribe(data => this.order = data);
      }
    });
  }
}