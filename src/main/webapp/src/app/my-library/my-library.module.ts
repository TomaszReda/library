import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyLibraryRoutingModule } from './my-library-routing.module';
import { MyLibraryComponent } from './my-library.component';

@NgModule({
  imports: [
    CommonModule,
    MyLibraryRoutingModule
  ],
  declarations: [MyLibraryComponent]
})
export class MyLibraryModule { }
