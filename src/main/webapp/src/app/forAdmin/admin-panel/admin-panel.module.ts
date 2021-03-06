import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminPanelRoutingModule } from './admin-panel-routing.module';
import { AdminPanelComponent } from './admin-panel.component';
import { BookListComponent } from './book-list/book-list.component';
import { LibraryListComponent } from './library-list/library-list.component';
import { UserListComponent } from './user-list/user-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    AdminPanelRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  declarations: [AdminPanelComponent, BookListComponent, LibraryListComponent, UserListComponent]
})
export class AdminPanelModule { }
