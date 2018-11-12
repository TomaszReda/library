import {Component, DoCheck, OnChanges, OnDestroy, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, NgForm} from "@angular/forms";
import {AuthService} from "./service/auth.service";
import {ModalComponent} from "angular-custom-modal";
import {User} from "./model/user/user.model";
import {HttpClient} from "@angular/common/http";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'webapp';

  private url: string = "http://localhost:8080/api/";

  @ViewChild("loginForm")
  private loginForm: NgForm;

  @ViewChild("login")
  public modalLogin: ModalComponent;

  public registerUserForm: FormGroup;

  public registerLibraryOwnerForm: FormGroup;

  constructor(public authService: AuthService, private http: HttpClient) {
  }

  ngOnInit(): void {

    if (localStorage.getItem("tokenID")) {
      this.http.get(this.url + "tokenValid").subscribe(
      x => {
        this.authService.islogin = true;

      }, error1 => {
        this.authService.logout();
      }
    )
    }

    this.registerUserForm = new FormGroup({
      firstname: new FormControl(null),
      lastname: new FormControl(null),
      email: new FormControl(null),
      phoneNumber: new FormControl(null),
      password: new FormControl(null)
    });

    this.registerLibraryOwnerForm = new FormGroup({
      firstname: new FormControl(null),
      lastname: new FormControl(null),
      email: new FormControl(null),
      phoneNumber: new FormControl(null),
      password: new FormControl(null)
    });
  }


  onLogin(loginForm: NgForm) {
    this.authService.login(loginForm.value.email, loginForm.value.password, this.modalLogin);
    this.loginForm.resetForm();
  }

  logout() {
    this.authService.logout();
  }

  registerUser() {
    const user: User = this.registerUserForm.getRawValue();
    this.authService.registerCasualUser(user);
  }

  registerLibraryOwner() {
    const user: User = this.registerLibraryOwnerForm.getRawValue();
    this.authService.registerLibraryOwner(user);
  }


  ngOnDestroy(): void {
    this.authService.logout();
  }


}

