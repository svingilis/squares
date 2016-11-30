import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { Observable } from 'rxjs/Rx'

import { ImportResult } from './import-result';

@Injectable()
export class ImportService {

  constructor(private http: Http) { }

  upload(file: File): Observable<ImportResult> {
    let formData = new FormData();
    formData.append('file', file);
    return this.http.post('http://localhost:8080/lists', formData)
      .map(res => res.json() as ImportResult);
  }

}