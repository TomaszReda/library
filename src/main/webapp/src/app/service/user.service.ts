import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  private url: string = 'http://localhost:8080/api/';

  getLoggerInfo(){
   return  this.http.get(this.url + 'online/user/info');
  }
}
