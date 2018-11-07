import {Component, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthService} from "./service/auth.service";
import {ModalModule} from "angular-custom-modal";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'webapp';



  constructor(public authService: AuthService) {

  }


  onLogin(loginForm: NgForm) {
    this.authService.login(loginForm.value.email, loginForm.value.password);

  }

  logout() {
    this.authService.logout();
  }

}

