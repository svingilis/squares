import { Point } from './point';
import { List } from './list';

export class ListRequest {
  list: List;
  
  points: Point[];
  
  constructor(name: string, points: Point[]) {
    this.list = new List(name);
    this.points = points;
  }
}