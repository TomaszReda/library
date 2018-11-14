import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ModalComponent} from "angular-custom-modal";
import {Credentials} from "../model/user/Credentials";
import {User} from "../model/user/user.model";
import {FormGroup, NgForm} from "@angular/forms";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public islogin: boolean = false;

  public badLogin: string = null;

  public badRegisterCasual: string = null;

  public badRegisterLibrary: string = null;

  public user: User;


  constructor(private http: HttpClient, private router: Router, private userService: UserService) {
  }

  url: string = "http://localhost:8080/api/";

  login(email: string, password: string, modalLogin: ModalComponent) {
    const creditians = {
      email,
      password
    };
    this.http.post(this.url + "login", creditians).subscribe((x: Credentials) => {
      localStorage.setItem("tokenID", x.token)
      modalLogin.close();
      this.badLogin = null;
      this.islogin = true;
      this.userService.getLoggerInfo().subscribe((x: User) => {
        this.user = x;
      })
    }, error1 => {
      localStorage.removeItem("tokenID");
      this.badLogin = 'Podaj poprawny login i hasło!';
      this.islogin = false;
    })
  }


  logout() {
    this.router.navigate(["/home"]);
    this.user = null;

    this.http.get(this.url + "logout").subscribe(x => {
      this.user = null;
      this.islogin = false;
      localStorage.removeItem("tokenID");
    })
  }

  registerCasualUser(user: User, form: FormGroup, modalRegister: ModalComponent) {
    this.http.post(this.url + "registerCasual", user).subscribe(x => {
      modalRegister.close();
      this.badRegisterCasual = null;
      form.reset();
    }, error1 => {
      this.badRegisterCasual = error1.error;
    });
  }

  registerLibraryOwner(user: User, form: FormGroup, modalRegister: ModalComponent) {
    this.http.post(this.url + "registerOwner", user).subscribe(x => {
      modalRegister.close();
      this.badRegisterLibrary = null;
      form.reset();
    }, error1 => {
      this.badRegisterLibrary = error1.error;
    })

  }

}
