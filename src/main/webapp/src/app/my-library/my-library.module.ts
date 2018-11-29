import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyLibraryRoutingModule } from './my-library-routing.module';
import { MyLibraryComponent } from './my-library.component';
import { MyLibraryListComponent } from './my-library-list/my-library-list.component';
import { MyLibraryDetailsComponent } from './my-library-details/my-library-details.component';

@NgModule({
  imports: [
    CommonModule,
    MyLibraryRoutingModule
  ],
  declarations: [MyLibraryComponent, MyLibraryListComponent, MyLibraryDetailsComponent]
})
export class MyLibraryModule { }
