import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ActivationUserRoutingModule } from './activation-user-routing.module';
import { ActivationUserComponent } from './activation-user.component';

@NgModule({
  imports: [
    CommonModule,
    ActivationUserRoutingModule
  ],
  declarations: [ActivationUserComponent]
})
export class ActivationUserModule { }
