import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MyLibraryComponent} from "./my-library.component";
import {MyLibraryListComponent} from "./my-library-list/my-library-list.component";
import {MyLibraryDetailsComponent} from "./my-library-details/my-library-details.component";
import {AddBookToLibraryComponent} from "./add-book-to-library/add-book-to-library.component";
import {SearchBookLibraryComponent} from "./search-book-library/search-book-library.component";
import {BookDetailsComponent} from "./book-details/book-details.component";

const routes: Routes = [
    {
      path: '',
      component: MyLibraryComponent,
      children: [
        {
          path: '',
          component: MyLibraryListComponent,

        }
      ]
    },
    {
      path: 'library/:libraryId',
      component: MyLibraryDetailsComponent,
    },
    {
      path: 'library/add/book',
      component: AddBookToLibraryComponent
    },
    {
      path: 'library/search/book',
      component: SearchBookLibraryComponent
    },
    {
      path:"library/book/:bookId/details",
      component: BookDetailsComponent
    }

  ]
;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyLibraryRoutingModule {
}
