import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs/Rx'

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

import { Point } from './point';
import { PointResponse } from './point-response';
import { List } from './list';

@Injectable()
export class PointService {

  public static readonly MAX_NUMBER_OF_POINTS = 10000;
  
  constructor(private http: Http) { }
  
  getListPoints(listId: number, page: number, size: number): Observable<PointResponse> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('page', String(page - 1)); // service returns 0 based index!
    params.set('size', String(size));
    console.log('params: ' + params);
    return this.http.get('http://localhost:8080/lists/' + listId + '/points', {search: params})
               .map(res => res.json() as PointResponse);
  }
  
  download(listId: number): Observable<Blob> {
    let headers = new Headers({ 'Accept': 'application/octet-stream' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get('http://localhost:8080/lists/' + listId + '/points', options)
      .map(res => new Blob([this.decode(res.arrayBuffer())], {type: 'text/plain;charset=UTF-8'}));
  }
  
  private decode(buf: ArrayBuffer) {
    return String.fromCharCode.apply(null, new Uint16Array(buf));
  }
  
}