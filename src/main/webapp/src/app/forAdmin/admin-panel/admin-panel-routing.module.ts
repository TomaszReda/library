import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdminPanelComponent} from "./admin-panel.component";
import {BookListComponent} from "./book-list/book-list.component";
import {LibraryListComponent} from "./library-list/library-list.component";
import {UserListComponent} from "./user-list/user-list.component";

const routes: Routes = [
  {
    path: '',
    component: AdminPanelComponent,
    children: [
      {
        path: "book/list",
        component: BookListComponent,
      },
      {
        path: "library/list",
        component: LibraryListComponent,
      },
      {
        path: "user/list",
        component: UserListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminPanelRoutingModule {
}
