import { Component, Input } from '@angular/core';

import { Point } from './point';

import { ImportService } from './import.service';
import { PointService } from './point.service';

@Component({
  selector: 'import-points',
  providers: [ImportService],
  template: `
        <h2>Import Points</h2>
        <div class="form-group">
          <label for="file">Points file</label>
          <input id="file" name="file" type="file" (change)="fileChangeEvent($event)">
          <p class="help-block">File encoding must be UTF-8</p>
        </div>
        <button (click)="upload()" class="btn btn-primary">Upload File</button>
  `
})

export class ImportPointsComponent {
  @Input()
  points: Point[];
  
  fileToUpload: File;

  constructor(private importService: ImportService) { }

  fileChangeEvent(fileInput: any) {
    this.fileToUpload = fileInput.target.files[0];
  }

  upload() {
    this.importService.upload(this.fileToUpload)
      .subscribe(
        res => { let msg = res.messages; this.addAll(res.points, msg); this.showMessages(msg); }
      );
  }
  
  addAll(points: Point[], messages: string[]) {
    points.every(p => {
      if (this.points.length === PointService.MAX_NUMBER_OF_POINTS) {
        messages.push('Max number of points reached');
        return false;
      }
      if (this.points.find(point => point.x === p.x && point.y === p.y)) {
        messages.push('Skipped already existing point ' + p.x + ' ' + p.y);
        return true;
      } else {
        this.points.push(p);
        return true;
      }
    });
  }

  showMessages(messages: string[]) {
    if (messages.length === 0) {
      return;
    }
    if (messages.length > 5) {
      let size = messages.length;
      messages = messages.slice(0, 5);
      messages.push('And ' + (size - 5) + ' other message(s)');
    }
    alert(messages);
  }
  
}
  
