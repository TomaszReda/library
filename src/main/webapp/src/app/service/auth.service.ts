import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Credentials} from "../model/user/Credentials";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public islogin: boolean = false;

  constructor(private http: HttpClient, private router: Router) {
  }

  url: string = "http://localhost:8080/";

  login(email: string, password: string,) {
    const creditians = {
      email,
      password
    };
    this.http.post(this.url + "login", creditians).subscribe(x => {
      this.islogin = true;
      this.router.navigate(["/home"]);
    }, error1 => {
      this.islogin = false;
    })
  }


  logout() {
    console.log("aaaa");
    this.router.navigate(["/home"]);
    console.log("aaaa");

    this.http.get(this.url + "logout").subscribe(x => {
      this.islogin = false;
    })
  }
}
