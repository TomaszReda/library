import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ActivationUserComponent} from "./activation-user.component";

const routes: Routes = [
  {
    path:"",
    component:ActivationUserComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ActivationUserRoutingModule { }

