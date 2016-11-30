import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response, } from '@angular/http';

import { Observable } from 'rxjs/Rx'

import { List } from './list';
import { Point } from './point';
import { ListRequest } from './list-request';
import { Square } from './square';

@Injectable()
export class ListService {

  constructor(private http: Http) { }
  
  getLists(): Observable<List[]> {
    return this.http.get('http://localhost:8080/lists')
      .map(response => response.json() as List[])
  }
  
  saveList(name: string, points: Point[]): Observable<List> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    let listRequest = new ListRequest(name, points);
    return this.http.post('http://localhost:8080/lists', listRequest, options)
      .map(response => response.json() as List);
  }
  
  findSquares(listId: number): Observable<Square[]> {
    console.log('fid squares for: ', listId);
    return this.http.get('http://localhost:8080/lists/' + listId + '/squares')
      .map(response => response.json() as Square[]);
  }
  
  deleteList(listId: number): Observable<List> {
    return this.http.delete('http://localhost:8080/lists/' + listId).map(response => response.json() as List);
  }
  
}