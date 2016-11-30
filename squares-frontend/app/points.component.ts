import { Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { PaginationInstance } from 'ng2-pagination';
import { Observable } from 'rxjs/Rx'

import 'rxjs/add/operator/do';
import 'rxjs/add/operator/switchMap'

import { Point } from './point';
import { PointResponse } from './point-response';

import { PointService } from './point.service';

@Component({
  selector: 'points',
  providers: [PointService],
  template: `
    <div class="container">
      <h1>Points</h1>
        <div>
          <div>
            <div>
              <a [routerLink]="['squares']" role="button" class="btn btn-primary">View Squares</a>
              <a (click)="downloadfile()" role="button" class="btn btn-primary">Export list</a>
            </div>
            <label for="total">Total points</label><span id="total"> {{config.totalItems}}</span>
          </div>
          <div>
            <label for="select">Show per page</label>
            <select id="select" [(ngModel)]="config.itemsPerPage" (ngModelChange)="onChange()">
              <option value="5">5</option>
              <option value="10">10</option>
              <option value="20">20</option>
              <option value="50">50</option>
            </select>
          </div>
        </div>
          <ul>
            <li *ngFor="let point of points | async | paginate: config">
              {{point | pointPipe}}
            </li>
          </ul>
      <pagination-controls (pageChange)="getPage($event)" id="point-list"></pagination-controls>
    </div>
  `
})
export class PointsComponent implements OnInit {
  points: Observable<Point[]>;

  config: PaginationInstance = {
    id: 'point-list',
    itemsPerPage: 20,
    currentPage: 1
  }

  constructor(
    private pointService: PointService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.onChange();
  }
  
  onChange() {
    this.getPage(1);
  }
  
  getPage(pageNumber: number) {
    this.config.currentPage = pageNumber;
    this.points = this.route.params.switchMap((params: Params) => this.pointService.getListPoints(+params['id'], this.config.currentPage, this.config.itemsPerPage))
      .do(res => {
        this.config.totalItems = res.totalElements;
        this.config.itemsPerPage = res.size;
        this.config.currentPage = res.number + 1; // service returns 0 based index!
      })
      .map(res => res.content);
  }
  
  downloadfile() {
    this.route.params.switchMap((params: Params) => this.pointService.download(+params['id']))
      .subscribe(data => this.saveFile(data));
  }
  
  private saveFile(data: Blob) {
    saveAs(data, 'export.txt', true);
  }
  
}