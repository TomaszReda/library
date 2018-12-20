import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BookedBookComponent} from "./booked-book.component";
import {BookedBookListComponent} from "./booked-book-list/booked-book-list.component";

const routes: Routes = [
  {
    path: "",
    component: BookedBookComponent,
    children: [{
      path: "",
      component: BookedBookListComponent
    }]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookedBookRoutingModule {
}
