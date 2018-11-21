import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AddLibraryRoutingModule } from './add-library-routing.module';
import { AddLibraryComponent } from './add-library.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AddLibraryRoutingModule
  ],
  declarations: [AddLibraryComponent]
})
export class AddLibraryModule { }
