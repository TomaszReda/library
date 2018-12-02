import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MyLibraryComponent} from "./my-library.component";
import {MyLibraryListComponent} from "./my-library-list/my-library-list.component";
import {MyLibraryDetailsComponent} from "./my-library-details/my-library-details.component";
import {AddBookToLibraryComponent} from "./add-book-to-library/add-book-to-library.component";

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
    }

  ]
;

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyLibraryRoutingModule {
}
