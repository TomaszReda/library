import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyLibraryRoutingModule } from './my-library-routing.module';
import { MyLibraryComponent } from './my-library.component';
import { MyLibraryListComponent } from './my-library-list/my-library-list.component';
import { MyLibraryDetailsComponent } from './my-library-details/my-library-details.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { AddBookToLibraryComponent } from './add-book-to-library/add-book-to-library.component';

@NgModule({
  imports: [
    CommonModule,
    MyLibraryRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [MyLibraryComponent, MyLibraryListComponent, MyLibraryDetailsComponent, AddBookToLibraryComponent]
})
export class MyLibraryModule { }
