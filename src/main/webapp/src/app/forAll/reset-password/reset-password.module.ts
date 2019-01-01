import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ResetPasswordRoutingModule } from './reset-password-routing.module';
import { ResetPasswordComponent } from './reset-password.component';
import { ResetPasswordMessageComponent } from './reset-password-message/reset-password-message.component';

@NgModule({
  imports: [
    CommonModule,
    ResetPasswordRoutingModule
  ],
  declarations: [ResetPasswordComponent, ResetPasswordMessageComponent,]
})
export class ResetPasswordModule { }
