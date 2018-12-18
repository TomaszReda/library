import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../model/user/user.model";
import {PasswordRequest} from "../model/user/changePassword.model";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  private url: string = environment.url;


  getLoggerInfo() {
    return this.http.get(this.url + '/online/user/info');
  }


  changeSettings(user: User) {
    return this.http.put(this.url + "/user/change/settings", user);
  }

  changePassword(changepas: PasswordRequest) {
    return this.http.put(this.url + "/user/change/password", changepas);
  }

  userInfo(uuid) {
    return this.http.get(this.url + "/user/" + uuid);
  }
}
