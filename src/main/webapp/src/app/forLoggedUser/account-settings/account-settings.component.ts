import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user/user.model";
import {FormControl, FormGroup, NgForm} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {Credentials} from "../../model/user/Credentials";
import {environment} from "../../../environments/environment.prod";

@Component({
  selector: 'app-account-settings',
  templateUrl: './account-settings.component.html',
  styleUrls: ['./account-settings.component.css']
})
export class AccountSettingsComponent implements OnInit {


  private url: string = environment.url;

  public settingsForm: FormGroup;

  public passwordChangeForm: FormGroup;

  public badSettings: string;

  public badPassword: string;

  public succesSettings: string;

  public succesPassword: string;

  constructor(private http: HttpClient, private  userService: UserService, private authService: AuthService) {
    this.createFormsPassword();
    this.createFormsSettings();

  }

  ngOnInit() {

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
      (x: User) => {
        let email: string = x.email;
        let password: string = x.password;

        let creditians = {
          email,
          password
        };
        this.http.post(this.url + "/login", creditians).subscribe((x: Credentials) => {
          localStorage.setItem("tokenID", x.token)
        }, error1 => {
          localStorage.removeItem("tokenID");
        })


        this.badSettings = null;
        this.succesSettings = "Zaloguj sie ponownie żeby wszelkie zmiany chodziły!";
      }, error1 => {
        this.badSettings = error1.error;
        this.succesSettings = null;
      }
    );

  }

  changePassword() {
    this.userService.changePassword(this.passwordChangeForm.getRawValue()).subscribe(
      x => {
        this.succesPassword = "Zmiany zostały dokonane!"
        this.badPassword = null;
        this.createFormsPassword();
      }, error1 => {
        this.badPassword = error1.error;
        this.succesPassword = null;
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
