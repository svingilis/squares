import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';
import { RouterModule }   from '@angular/router';

import { Ng2PaginationModule } from 'ng2-pagination';

import { AppComponent }  from './app.component';
import { PointsComponent } from './points.component'
import { CreatePointComponent } from './create-point.component'
import { CreateListComponent } from './create-list.component'
import { ViewSavedListsComponent } from './view-saved-lists.component'
import { ImportPointsComponent } from './import-points.component'
import { SquaresComponent } from './squares.component'

import { PointPipe } from './point.pipe';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    Ng2PaginationModule,
    RouterModule.forRoot([
      {
        path: 'create-list',
        component: CreateListComponent
      },
      {
        path: 'view-saved-lists',
        component: ViewSavedListsComponent
      },
      {
        path: 'list/:id',
        component: PointsComponent
      },
      {
        path: 'list/:id/squares',
        component: SquaresComponent
      }
    ])
  ],
  declarations: [
    AppComponent,
    PointsComponent,
    CreatePointComponent,
    CreateListComponent,
    ViewSavedListsComponent,
    ImportPointsComponent,
    SquaresComponent,
    PointPipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
