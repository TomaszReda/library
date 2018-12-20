import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MyReservBookComponent} from "./my-reserv-book.component";
import {MyReservBookListComponent} from "./my-reserv-book-list/my-reserv-book-list.component";

const routes: Routes = [
  {
    path: "",
    component: MyReservBookComponent,
    children: [
      {
        path: "",
        component: MyReservBookListComponent
      }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyReservBookRoutingModule {
}
