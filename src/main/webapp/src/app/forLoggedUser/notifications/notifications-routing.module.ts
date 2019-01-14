import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "../../forAll/home/home.component";
import {NotificationsComponent} from "./notifications.component";

const routes: Routes = [
  {
    path:"",
    component: NotificationsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NotificationsRoutingModule { }
