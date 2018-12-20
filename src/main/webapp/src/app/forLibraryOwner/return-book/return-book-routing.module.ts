import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ReturnBookComponent} from "./return-book.component";
import {ReturnBookUserComponent} from "./return-book-user/return-book-user.component";
import {ReservBookListComponent} from "../reserv/reserv-book-list/reserv-book-list.component";
import {ReturnBookListComponent} from "./return-book-list/return-book-list.component";

const routes: Routes = [
  {
    path: "",
    component: ReturnBookComponent,
    children: [{
      path: "",
      component: ReturnBookUserComponent
    }]
  },
  {
    path:"user/:userId",
    component: ReturnBookListComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReturnBookRoutingModule {
}
