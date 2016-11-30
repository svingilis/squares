import { Point } from './point';

export class PointResponse {
  content: Point[];
  size: number; // items per page
  totalElements: number;
  number: number;
}