import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LibraryDetailsComponent} from "./library-details.component";

const routes: Routes = [
  {
    path:"",
    component: LibraryDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LibraryDetailsRoutingModule { }
