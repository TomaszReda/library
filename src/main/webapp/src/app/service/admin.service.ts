import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.prod";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private url: string = environment.url;

  constructor(private http: HttpClient) {
  }

  initParam(page,size) {
    return new HttpParams().append('page', page).append("size", size);
  }

  getAllBook(page, size) {
    return this.http.get(this.url + "/book/get/all",{params: this.initParam(page,size)});
  }

  getAllUser(page, size) {
    return this.http.get(this.url + "/user/get/all",{params: this.initParam(page,size)});
  }

  getAllLibrary(page, size) {
    return this.http.get(this.url + "/library/get/all",{params: this.initParam(page,size)});
  }


}
