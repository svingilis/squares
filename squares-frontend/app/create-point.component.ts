import { Component, Input, OnInit } from '@angular/core';

import { Point } from './point';

import { PointService } from './point.service';

@Component({
  selector: 'create-point',
  template: `
        <h2>Add new Point</h2>
        <form name="add-point" #addPointForm="ngForm" (ngSubmit)="onSubmit()">
          <div class="form-group">
            <label for="x">X coordinate</label>
            <input [(ngModel)]="newPoint.x" type="number" class="form-control" id="x" name="x" required min="-5000" max="5000">
          </div>
          <div class="form-group">
            <label for="y">Y coordinate</label>
            <input [(ngModel)]="newPoint.y" type="number" class="form-control" id="y" name="y" required min="-5000" max="5000">
          </div>
          <button type="submit" class="btn btn-primary">Add Point</button>
        </form>
  `
})
  
export class CreatePointComponent implements OnInit {
  @Input()
  points: Point[];
  
  newPoint: Point;
  
  ngOnInit() {
    this.newPoint = new Point();
  }
  
  onSubmit() {
    if (this.points.length === PointService.MAX_NUMBER_OF_POINTS) {
      alert('Cannot add more points!');
      return;
    }
    if (this.points.find(point => point.x === this.newPoint.x && point.y === this.newPoint.y)) {
      alert('Such point already exists!');
      return;
    }
    this.points.push(this.newPoint);
    this.newPoint = new Point();
  }
}