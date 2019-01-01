import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ModalComponent} from "angular-custom-modal";
import {Credentials} from "../model/user/Credentials";
import {User} from "../model/user/user.model";
import {FormGroup} from "@angular/forms";
import {UserService} from "./user.service";
import {UserRoles} from "../model/user/user.roles.model";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public islogin: boolean = false;

  public isLibraryOwner: boolean = false;

  public badLogin: string = null;

  public badRegisterCasual: string = null;

  public badRegisterLibrary: string = null;

  public user: User;

  public pharmacyOwner:boolean = false;

  public admin: boolean = false;

  public casualUser: boolean = false;

  public badResetPassowrdEmail: string = null;

  public sendResetPasswordEmail: string = null;


  constructor(private http: HttpClient, private router: Router, private userService: UserService) {
  }

  url: string = environment.url;

  login(email: string, password: string, modalLogin: ModalComponent) {
    const creditians = {
      email,
      password
    };
    this.http.post(this.url + "/login", creditians).subscribe((x: Credentials) => {
      localStorage.setItem("tokenID", x.token)
      modalLogin.close();
      this.badLogin = null;
      this.islogin = true;
      this.userService.getLoggerInfo().subscribe((x: User) => {
        this.user = x;
        let owner: UserRoles = new UserRoles("LIBRARY_OWNER");
        owner.userRole = "LIBRARY_OWNER";

        let admin: UserRoles = new UserRoles("ADMIN");
        admin.userRole = "ADMIN";

        let casualsUser: UserRoles = new UserRoles("CASUAL_USER");
        casualsUser.userRole = "CASUAL_USER";

        for (let i = 0; i < this.user.userRoles.length; i++) {
          if (this.user.userRoles[i].userRole === "LIBRARY_OWNER") {
            this.pharmacyOwner=true;
          }
          if (this.user.userRoles[i].userRole === "CASUAL_USER") {
            this.casualUser = true;
          }
          if (this.user.userRoles[i].userRole === "ADMIN") {
            this.admin = true;
          }
        }


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
    this.pharmacyOwner=null;
    this.casualUser = null;
    this.admin = null;
    this.http.get(this.url + "/logout").subscribe(x => {
      this.user = null;
      this.islogin = false;
      localStorage.removeItem("tokenID");
    })
  }

  registerCasualUser(user: User, form: FormGroup, modalRegister: ModalComponent) {
    this.http.post(this.url + "/registerCasual", user).subscribe(x => {
      modalRegister.close();
      this.badRegisterCasual = null;
      form.reset();
    }, error1 => {
      this.badRegisterCasual = error1.error;
    });
  }

  registerLibraryOwner(user: User, form: FormGroup, modalRegister: ModalComponent) {
    this.http.post(this.url + "/registerOwner", user).subscribe(x => {
      modalRegister.close();
      this.badRegisterLibrary = null;
      form.reset();
    }, error1 => {
      this.badRegisterLibrary = error1.error;
    })
  }

  resetPasswordEmail(email, forgettingPasswordForm) {
    this.http.post(this.url + "/user/send/reset/password/email", email).subscribe(x => {
      this.badResetPassowrdEmail = null;
      this.sendResetPasswordEmail = "Wysłano na podany email link do resetu hasła!"
    }, error1 => {
      this.badResetPassowrdEmail = error1.error;
      this.sendResetPasswordEmail = null;
    })

  }

}
