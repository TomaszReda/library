import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyReservBookRoutingModule } from './my-reserv-book-routing.module';
import { MyReservBookComponent } from './my-reserv-book.component';
import { MyReservBookListComponent } from './my-reserv-book-list/my-reserv-book-list.component';

@NgModule({
  imports: [
    CommonModule,
    MyReservBookRoutingModule
  ],
  declarations: [MyReservBookComponent, MyReservBookListComponent]
})
export class MyReservBookModule { }
