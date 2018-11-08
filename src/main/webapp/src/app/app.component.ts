import {Component, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthService} from "./service/auth.service";
import {ModalComponent} from "angular-custom-modal";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'webapp';


  @ViewChild("loginForm")
  private loginForm: NgForm;

  @ViewChild("login")
  public modalLogin: ModalComponent;

  constructor(public authService: AuthService) {
  }

  onLogin(loginForm: NgForm) {
    this.authService.login(loginForm.value.email, loginForm.value.password, this.modalLogin);
    this.loginForm.resetForm();
  }

  logout() {
    this.authService.logout();
  }

}

