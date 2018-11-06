import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/user.model";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  private userUrl = 'http://localhost:8080/api';

  public getUsers(): Observable<Array<User>> {
    return this.http.get<Array<User>>(this.userUrl + '/users', {headers: httpOptions.headers});
  }

}
