import {Component, OnInit} from '@angular/core';
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

    // pagination
    defaultPageSize = 10;
    currentPage = 1;
    pageSize = this.defaultPageSize;
    totalSize = 0;

    constructor(private readonly orderHistoryService: OrderHistoryService) {
    }

    ngOnInit(): void {
        this.handleList();
    }

    handleList() {
        this.orderHistoryService.getOrdersHistory(this.currentPage - 1, this.pageSize).subscribe(data => {
            this.orders = data.content;
            this.currentPage = data.number + 1;
            this.pageSize = data.size;
            this.totalSize = data.totalElements;
        });
    }

    changePageSize(event: Event) {
        this.pageSize = +(event.target as HTMLInputElement).value;
        this.currentPage = 1;
        this.handleList()
    }
}