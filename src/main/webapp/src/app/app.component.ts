import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {AuthService} from "./service/auth.service";
import {ModalComponent} from "angular-custom-modal";
import {User} from "./model/user/user.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "./service/user.service";
import {environment} from "../environments/environment.prod";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'webapp';

  private url: string = environment.url;

  @ViewChild("loginForm")
  private loginForm: NgForm;

  @ViewChild("login")
  public modalLogin: ModalComponent;

  @ViewChild("casualUser")
  public casualUser: ModalComponent;

  @ViewChild("libraryOwner")
  public libraryOwner: ModalComponent;


  public registerUserForm: FormGroup;

  public registerLibraryOwnerForm: FormGroup;

  constructor(public authService: AuthService, private http: HttpClient, private userService: UserService) {
  }


  ngOnInit(): void {

    this.userService.getLoggerInfo().subscribe(x => {
      this.authService.user = x;
      this.authService.pharmacyOwner = null;
      this.authService.admin = null;
      this.authService.casualUser = null;
      if(this.authService.user!=null){
      for (let i = 0; i < this.authService.user.userRoles.length; i++) {
        if (this.authService.user.userRoles[i].userRole === "LIBRARY_OWNER") {
          this.authService.pharmacyOwner = true;
        }
        if (this.authService.user.userRoles[i].userRole === "CASUAL_USER") {
          this.authService.casualUser = true;
        }
        if (this.authService.user.userRoles[i].userRole === "ADMIN") {
          this.authService.admin = true;
        }
      }}
    })
    if (localStorage.getItem("tokenID")) {
      this.http.get(this.url + "/tokenValid").subscribe(
        x => {

          this.authService.islogin = true;

        }, error1 => {


          this.authService.logout();
        }
      )
    }

    this.registerUserForm = new FormGroup({
      firstname: new FormControl(null, [Validators.required]),
      lastname: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required]),
      phoneNumber: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required])
    });

    this.registerLibraryOwnerForm = new FormGroup({
      firstname: new FormControl(null, [Validators.required]),
      lastname: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required]),
      phoneNumber: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required])
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
    this.authService.registerCasualUser(user, this.registerUserForm, this.casualUser);
  }

  registerLibraryOwner() {
    const user: User = this.registerLibraryOwnerForm.getRawValue();
    this.authService.registerLibraryOwner(user, this.registerLibraryOwnerForm, this.libraryOwner);
  }


  ngOnDestroy(): void {
    this.authService.logout();
  }


}

