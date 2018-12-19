import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ReturnBookComponent} from "./return-book.component";
import {ReturnBookUserComponent} from "./return-book-user/return-book-user.component";

const routes: Routes = [
  {
    path: "",
    component: ReturnBookComponent,
    children: [{
      path: "",
      component: ReturnBookUserComponent
    }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReturnBookRoutingModule {
}
