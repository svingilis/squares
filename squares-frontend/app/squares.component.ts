import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, Params } from '@angular/router';

import { PaginationInstance } from 'ng2-pagination';
import { Observable } from 'rxjs/Rx'

import { Square } from './square';
import { ListService } from './list.service';

@Component({
  selector: 'squares',
  providers: [ListService],
  template: `
  <div class="container">
    <h1>Squares</h1>
    <div>
      <a (click)="goBack()" role="button" class="btn btn-primary">Back to List</a>
    </div>
    <label for="total">Total squares found</label><span id="total"> {{(squares | async)?.length}}</span>
    <div>
      <label for="select">Show per page</label>
      <select id="select" [(ngModel)]="config.itemsPerPage">
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="50">50</option>
      </select>
    </div>
    <ul>
      <li *ngFor="let square of squares | async | paginate: config">
        {{square.p1 | pointPipe}} {{square.p2 | pointPipe}} {{square.p3 | pointPipe}} {{square.p4 | pointPipe}}
      </li>
    </ul>
    <pagination-controls (pageChange)="onPageChange($event)" id="squares-list"></pagination-controls>
  </div>
  `
})

export class SquaresComponent implements OnInit {
  
  squares: Observable<Square[]>;
  
  config: PaginationInstance = {
    id: 'squares-list',
    itemsPerPage: 20,
    currentPage: 1
  }
  
  constructor(
    private listService: ListService,
    private location: Location,
    private route: ActivatedRoute
  ) { }
  
  ngOnInit() {
    this.squares = this.route.params.switchMap((params: Params) => this.listService.findSquares(+params['id']))
      .map(res => res);
  }

  onPageChange(pageNumber: number) {
    this.config.currentPage = pageNumber;
  }
  
  goBack(): void {
    this.location.back();
  }

}