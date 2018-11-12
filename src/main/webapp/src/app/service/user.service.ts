import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  private url: string = 'http://localhost:8080/api/';

  getLoggerInfo() {
    return this.http.get(this.url + 'online/user/info');
  }


  changeSettings(user: User) {
    console.log(user);
  }

  changePassword(cos) {
    console.log(cos);
  }
}
