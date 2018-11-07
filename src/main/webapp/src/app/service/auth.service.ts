import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Credentials} from "../model/user/Credentials";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  credentials: Credentials;

  constructor(private http: HttpClient) {
  }

  url: string = "http://localhost:8080/";

  login(email: string, password: string) {
    const creditians = {
      email,
      password
    };
    this.http.post(this.url+"login", creditians).subscribe(x => {
      console.log(x);
    })
  }
}
