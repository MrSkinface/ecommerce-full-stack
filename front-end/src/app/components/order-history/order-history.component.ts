import { Component, OnInit } from '@angular/core';
import {OrderHistory} from "../../common/order-history";
import {OrderHistoryService} from "../../services/order-history.service";

@Component({
    selector: 'app-order-history',
    templateUrl: './order-history.component.html',
    styleUrls: ['./order-history.component.css'],
    standalone: false
})
export class OrderHistoryComponent implements OnInit {

  orders: OrderHistory[] = [];
  storage: Storage = sessionStorage;
  // pagination
  // pagination
  defaultPageSize = 10;
  currentPage = 1;
  pageSize = this.defaultPageSize;
  totalSize = 0;

  constructor(private history: OrderHistoryService) { }

  ngOnInit(): void {
    this.handleList();
  }

  handleList() {
    const data = this.storage.getItem('customer');
    if (data) {
      const customer = JSON.parse(data);
      this.history.getOrdersHistory(customer.id, this.currentPage -1, this.pageSize).subscribe(result => {
        this.orders = result._embedded.orders;
        this.currentPage = result.page.number + 1;
        this.pageSize = result.page.size;
        this.totalSize = result.page.totalElements;
      });
    }
  }

  changePageSize(event: Event) {
    this.pageSize = +(event.target as HTMLInputElement).value;
    this.currentPage = 1;
    this.handleList()
  }
}
