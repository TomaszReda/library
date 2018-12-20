import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ReservComponent} from "./reserv.component";
import {ReservUserListComponent} from "./reserv-user-list/reserv-user-list.component";
import {ReservBookListComponent} from "./reserv-book-list/reserv-book-list.component";

const routes: Routes = [
  {
    path: "",
    component: ReservComponent,
    children: [
      {
        path: "",
        component: ReservUserListComponent,
      },
    ]
  },
  {
    path:"book/user/:userId",
    component: ReservBookListComponent
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReservRoutingModule { }
