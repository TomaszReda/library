import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ResetPasswordMessageComponent} from "./reset-password-message/reset-password-message.component";

const routes: Routes = [
  {
    path: "",
    component: ResetPasswordMessageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ResetPasswordRoutingModule { }
