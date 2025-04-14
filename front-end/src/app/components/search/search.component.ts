import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.css'],
    standalone: false
})
export class SearchComponent implements OnInit {

  constructor(private router: Router) { }

  search(query: string) {
    this.router.navigateByUrl(`/search/${query}`);
  }

  ngOnInit(): void {
  }

}
