import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AddLibraryRoutingModule } from './add-library-routing.module';
import { AddLibraryComponent } from './add-library.component';

@NgModule({
  imports: [
    CommonModule,
    AddLibraryRoutingModule
  ],
  declarations: [AddLibraryComponent]
})
export class AddLibraryModule { }
