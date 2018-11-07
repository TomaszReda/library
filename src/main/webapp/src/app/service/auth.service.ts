import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Credentials} from "../model/user/Credentials";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public islogin: boolean = false;

  constructor(private http: HttpClient) {
  }

  url: string = "http://localhost:8080/";

  login(email: string, password: string) {
    const creditians = {
      email,
      password
    };
    this.http.post(this.url + "login", creditians).subscribe(x => {
      this.islogin = true;
    }, error1 => {
      this.islogin = false;
    })
  }


  logout() {
    this.http.get(this.url + "logout").subscribe(x => {
      this.islogin = false;
    })
  }
}
