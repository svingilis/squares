import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { List } from './list';
import { ListService } from './list.service';

@Component({
  selector: 'view-saved-lists',
  providers: [ListService],
  template: `
  <div class="container">
    <h1>View saved Lists</h1>
    <div class="row">
      <ul>
        <div *ngIf="!lists || lists.length === 0">There are no saved Lists!</div>
        <li *ngFor="let list of lists">
          <a [routerLink]="['/list', list.id]">
            <span>{{list.name}}</span>
          </a>
          <a (click)="deleteList(list.id)"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>
        </li>
      </ul>
    </div>
  </div>
  `
})

export class ViewSavedListsComponent implements OnInit {
  lists: List[];

  constructor(
    private listService: ListService,
    private router: Router) { }

  ngOnInit() {
    this.listService.getLists().subscribe(lists => this.lists = lists);
  }

  deleteList(listId: number) {
    this.listService.deleteList(listId).subscribe(res => this.listService.getLists().subscribe(lists => this.lists = lists));
  }
  
}