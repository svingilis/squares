import { Component} from '@angular/core';

@Component({
  selector: 'my-app',
  template: `
  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <ul class="nav navbar-nav">
        <li routerLinkActive="active"><a routerLink="/create-list">Create New List</a></li>
        <li routerLinkActive="active"><a routerLink="/view-saved-lists">View Saved Lists</a></li>
      </ul>
    </div>
  </nav>
  <router-outlet></router-outlet>
  `
})
export class AppComponent {
}
