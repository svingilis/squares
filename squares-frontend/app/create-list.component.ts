import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { PaginationInstance } from 'ng2-pagination';

import { Point } from './point';
import { ListRequest } from './list-request';

import { ListService } from './list.service';

@Component({
  selector: 'create-list',
  providers: [ListService],
  template: `
    <div class="container">
      <h1>Create New List</h1>
      <div class="row">
        <div class="col-md-4">
          <h2>Save current List as</h2>
          <form name="save-list" #saveListForm="ngForm" (ngSubmit)="onSubmit()">
            <div class="form-group">
              <label for="name">List Name</label>
              <input [(ngModel)]="name" type="text" class="form-control" id="name" name="name" required>
            </div>
            <button type="submit" class="btn btn-primary">Save List</button>
            <a class="btn btn-danger" (click)="clearAll()" role="button">Clear all Points</a>
          </form>
        </div>
        <div class="col-md-4">
          <create-point [points]="points"></create-point>
        </div>
        <div class="col-md-4">
          <import-points [points]="points"></import-points>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <h2>Points</h2>
          <label for="select">Show per page</label>
          <select id="select" [(ngModel)]="config.itemsPerPage">
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
          <ul>
            <li *ngFor="let point of points | paginate: config">
              {{point | pointPipe}} <a (click)="remove(point)"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
            </li>
          </ul>
          <pagination-controls (pageChange)="onPageChange($event)" id="create-list"></pagination-controls>
        </div>
      </div>
    </div>
  `
})

export class CreateListComponent implements OnInit {
  points: Point[];
  
  name: string;
  
  config: PaginationInstance = {
    id: 'create-list',
    itemsPerPage: 20,
    currentPage: 1
  }
  
  constructor(
    private listService: ListService,
    private router: Router
  ) { }
  
  ngOnInit() {
    this.points = [];
  }
  
  onPageChange(pageNumber: number) {
    this.config.currentPage = pageNumber;
  }
  
  onSubmit() {
    this.listService.saveList(this.name, this.points).subscribe(res => this.router.navigate(['list', res.id]));
  }
  
  clearAll() {
    this.points = [];
  }
  
  remove(point: Point) {
    let index = this.points.indexOf(point);
    if (index > -1) {
      this.points.splice(index, 1);
    }
  }
}