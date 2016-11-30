import { Pipe, PipeTransform } from '@angular/core';
import { Point } from './point';

@Pipe({name: 'pointPipe'})
export class PointPipe implements PipeTransform {
  transform(point: Point): string {
    return '(' + point.x + ', ' + point.y + ')'; 
  }
}