import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ModalComponent} from "angular-custom-modal";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public islogin: boolean = false;

  public badLogin: string = null;

  constructor(private http: HttpClient, private router: Router) {
  }

  url: string = "http://localhost:8080/api/";

  login(email: string, password: string, modalLogin: ModalComponent) {
    const creditians = {
      email,
      password
    };
    this.http.post(this.url + "login", creditians).subscribe(x => {
      modalLogin.close();
      this.badLogin = null;
      this.islogin = true;
    }, error1 => {
      this.badLogin = 'Podaj poprawny login i hasło';
      this.islogin = false;
    })
  }


  logout() {
    this.router.navigate(["/home"]);

    this.http.get(this.url + "logout").subscribe(x => {
      this.islogin = false;
    })
  }
}
