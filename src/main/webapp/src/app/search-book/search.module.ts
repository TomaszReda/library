import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SearchRoutingModule } from './search-routing.module';
import { SearchComponent } from './search.component';
import { SearchBookListComponent } from './search-book-list/search-book-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { SearchBookDetailsComponent } from './search-book-details/search-book-details.component';

@NgModule({
  imports: [
    CommonModule,
    SearchRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  declarations: [SearchComponent, SearchBookListComponent, SearchBookDetailsComponent]
})
export class SearchModule { }
