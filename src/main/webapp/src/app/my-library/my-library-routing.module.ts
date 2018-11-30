import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MyLibraryComponent} from "./my-library.component";
import {MyLibraryListComponent} from "./my-library-list/my-library-list.component";
import {MyLibraryDetailsComponent} from "./my-library-details/my-library-details.component";

const routes: Routes = [
  {
    path: '',
    component: MyLibraryComponent,
    children: [
      {
        path: '',
        component: MyLibraryListComponent,

      }
    ]},
  {
    path: 'library/:libraryId',
    component: MyLibraryDetailsComponent,
  },
  {
    path: 'costam',
    component: MyLibraryDetailsComponent
  }

]
;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyLibraryRoutingModule {
}
