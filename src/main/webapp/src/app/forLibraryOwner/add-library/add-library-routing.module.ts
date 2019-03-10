import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AddLibraryComponent} from "./add-library.component";

const routes: Routes = [
  {
    path: '',
    component: AddLibraryComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddLibraryRoutingModule {
}
