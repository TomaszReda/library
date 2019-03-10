import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyReservBookRoutingModule } from './my-reserv-book-routing.module';
import { MyReservBookComponent } from './my-reserv-book.component';
import { MyReservBookListComponent } from './my-reserv-book-list/my-reserv-book-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    MyReservBookRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [MyReservBookComponent, MyReservBookListComponent]
})
export class MyReservBookModule { }
