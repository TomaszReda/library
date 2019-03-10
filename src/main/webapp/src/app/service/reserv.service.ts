import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class ReservService {

  private url=environment.url;
  constructor(private http:HttpClient) { }

  getReservUserDetails(email){
    return this.http.get(this.url+"/search/user/"+email);
  }
}
