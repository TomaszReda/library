import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookedBookRoutingModule } from './booked-book-routing.module';
import { BookedBookComponent } from './booked-book.component';
import { BookedBookListComponent } from './booked-book-list/booked-book-list.component';
import { BookedBookListDetailsComponent } from './booked-book-list-details/booked-book-list-details.component';

@NgModule({
  imports: [
    CommonModule,
    BookedBookRoutingModule
  ],
  declarations: [BookedBookComponent, BookedBookListComponent, BookedBookListDetailsComponent]
})
export class BookedBookModule { }
