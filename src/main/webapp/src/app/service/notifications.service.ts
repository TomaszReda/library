import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  constructor(private http: HttpClient) {
  }

  private url: string = environment.url;


  public getAllUnreadNotificationForCasualUser(page, size) {
    let params = new HttpParams().append('page', page).append("size", size);
    return this.http.get(this.url + "/notification/for/casual/user", {params: params})
  }

  public getAllUnreadNotificationForLibraryOwner(page, size) {
    let params = new HttpParams().append('page', page).append("size", size);
    return this.http.get(this.url + "/notification/for/library/owner", {params: params});
  }

  public readNotification(messageId) {
    const notificationRequest = {
      messageId: messageId
    }
    return this.http.post(this.url + "/read/notification", notificationRequest);
  }

  public getUnreadNotificationPost(email) {
    return this.http.post(this.url + "/get/unread/notification",email);

  }

  public getUnreadNotificationGet() {
    return this.http.get(this.url + "/get/unread/notification");

  }

  public readAllNotification(){
    return this.http.get(this.url+"/read/all/notification");
  }





  }
