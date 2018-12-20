import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TermsRoutingModule } from './terms-routing.module';
import { TermsComponent } from './terms.component';

@NgModule({
  imports: [
    CommonModule,
    TermsRoutingModule
  ],
  declarations: [TermsComponent]
})
export class TermsModule { }
