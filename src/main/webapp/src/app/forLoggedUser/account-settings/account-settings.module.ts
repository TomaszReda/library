import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountSettingsRoutingModule } from './account-settings-routing.module';
import { AccountSettingsComponent } from './account-settings.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    AccountSettingsRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [AccountSettingsComponent]
})
export class AccountSettingsModule { }
