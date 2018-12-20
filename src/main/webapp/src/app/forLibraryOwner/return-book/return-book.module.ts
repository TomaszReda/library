import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReturnBookRoutingModule } from './return-book-routing.module';
import { ReturnBookComponent } from './return-book.component';
import { ReturnBookUserComponent } from './return-book-user/return-book-user.component';
import { ReturnBookListComponent } from './return-book-list/return-book-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    ReturnBookRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  declarations: [ReturnBookComponent, ReturnBookUserComponent, ReturnBookListComponent]
})
export class ReturnBookModule { }
