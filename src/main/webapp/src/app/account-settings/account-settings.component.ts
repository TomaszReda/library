import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "../service/user.service";
import {User} from "../model/user/user.model";
import {FormControl, FormGroup, NgForm} from "@angular/forms";

@Component({
  selector: 'app-account-settings',
  templateUrl: './account-settings.component.html',
  styleUrls: ['./account-settings.component.css']
})
export class AccountSettingsComponent implements OnInit {


  private url: string = 'http://localhost:8080/api/';

  public settingsForm: FormGroup;

  public passwordChangeForm: FormGroup;

  constructor(private http: HttpClient, private  userService: UserService) {


  }

  ngOnInit() {
    this.createFormsPassword();
    this.createFormsSettings();
  }


  createFormsSettings() {
    this.settingsForm = new FormGroup({
      id: new FormControl(null),
      firstname: new FormControl(null),
      lastname: new FormControl(null),
      phoneNumber: new FormControl(null),
      email: new FormControl(null),
      password: new FormControl("")
    });


    this.userService.getLoggerInfo().subscribe((x: User) => {
      this.settingsForm = new FormGroup({
        id: new FormControl(x.id),
        firstname: new FormControl(x.firstname),
        lastname: new FormControl(x.lastname),
        phoneNumber: new FormControl(x.phoneNumber),
        email: new FormControl(x.email),
        password: new FormControl("")
      })
    });
  }

  createFormsPassword() {
    this.passwordChangeForm = new FormGroup({
      oldpassword: new FormControl(null),
      newpassword: new FormControl(null),
      newpasswordrepeat: new FormControl(null),
    })
  }


  changeSettings() {
    this.userService.changeSettings(this.settingsForm.getRawValue()).subscribe(
      x => {
        console.log(x);
      }
    );

  }

  changePassword() {
    this.userService.changePassword(this.passwordChangeForm.getRawValue()).subscribe(
      x => {
        console.log(x);
      }
    );

  }

  resetSettings() {
    this.createFormsSettings();
  }

  resetPassword() {
    this.createFormsPassword();
  }
}
