import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private url: string = environment.url;


  constructor(private httpClient: HttpClient) {
  }

  search(word, page, size) {
    let libraryId = localStorage.getItem("libraryId")
    let params = new HttpParams().append("word", word).append('page', page).append("size", size);
    return this.httpClient.get(this.url + "/search/library/" + libraryId + "/book", {params: params});
  }

  searchCasualUser(word, page, size) {
    let params = new HttpParams().append("word", word).append('page', page).append("size", size);
    return this.httpClient.get(this.url + "/search/book", {params: params});
  }

  searchReservBook(word, page, size) {
    let params = new HttpParams().append("word", word).append('page', page).append("size", size);
    return this.httpClient.get(this.url + "/search/reserv/book", {params: params});
  }

  searchUserReservBook(userId,page, size) {
    let params = new HttpParams().append('page', page).append("size", size);
    return this.httpClient.get(this.url + "/search/user/" + userId + "/reserv/book", {params: params});
  }

  searchBookedBook(page,size){
    let params = new HttpParams().append('page', page).append("size", size);
    return this.httpClient.get(this.url + "/get/booked/book", {params: params});
  }

}
