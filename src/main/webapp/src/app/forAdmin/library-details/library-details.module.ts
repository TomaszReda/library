import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LibraryDetailsRoutingModule } from './library-details-routing.module';
import { LibraryDetailsComponent } from './library-details.component';

@NgModule({
  imports: [
    CommonModule,
    LibraryDetailsRoutingModule
  ],
  declarations: [LibraryDetailsComponent]
})
export class LibraryDetailsModule { }
