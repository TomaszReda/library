import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReservRoutingModule } from './reserv-routing.module';
import { ReservComponent } from './reserv.component';
import { ReservUserListComponent } from './reserv-user-list/reserv-user-list.component';
import { ReservBookListComponent } from './reserv-book-list/reserv-book-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    ReservRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [ReservComponent, ReservUserListComponent, ReservBookListComponent]
})
export class ReservModule { }
