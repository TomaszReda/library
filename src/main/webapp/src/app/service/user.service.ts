import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user/user.model";
import {PasswordRequest} from "../model/user/changePassword.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  private url: string = 'http://localhost:8080/api';

  getLoggerInfo() {
    return this.http.get(this.url + '/online/user/info');
  }


  changeSettings(user: User) {
    return this.http.post(this.url+"/user/change/settings",user);
  }

  changePassword(changepas:PasswordRequest) {
    return this.http.post(this.url+"/user/change/password",changepas);
  }
}
