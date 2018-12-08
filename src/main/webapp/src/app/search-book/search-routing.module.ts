import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {SearchComponent} from "./search.component";
import {MyLibraryComponent} from "../my-library/my-library.component";
import {MyLibraryListComponent} from "../my-library/my-library-list/my-library-list.component";
import {SearchBookListComponent} from "./search-book-list/search-book-list.component";
import {SearchBookDetailsComponent} from "./search-book-details/search-book-details.component";

const routes: Routes = [
  {
    path: '',
    component: SearchComponent,
    children: [
      {
        path: '',
        component: SearchBookListComponent,

      }
    ]
  },
  {
    path:":bookId",
    component:SearchBookDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchRoutingModule {
}
