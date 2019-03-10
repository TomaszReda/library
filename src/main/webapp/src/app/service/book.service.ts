import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private  http: HttpClient) {
  }

  private url: string = environment.url;

  public addBook(bookRequest) {
    return this.http.post(this.url + "/book/add", bookRequest, {observe: 'response'});
  }

  public getDetails(bookId) {
    return this.http.get(this.url + "/book/" + bookId + "/details");
  }

  public deleteBook(bookId, quant) {

    return this.http.post(this.url + "/book/" + bookId + "/delete/", quant);
  }

  public getDetailsForCasualUser(bookId) {
    return this.http.get(this.url + "/book/details/" + bookId);
  }

  public reservBook(bookId, quant) {
    return this.http.post(this.url + "/book/" + bookId + "/reserv", quant);
  }

  public deleteReservBook(bookId) {
    return this.http.post(this.url + "/book/" + bookId + "/delete/reserv",null);
  }

  public deleteReserv(bookId){
    return this.http.get(this.url+"/book/"+bookId+"/reserv/delete");
  }

  public confirmReserv(bookId){
    return this.http.get(this.url+"/book/"+bookId+"/reserv/accept");
  }

  public bookReturn(bookId){
    return this.http.get(this.url+"/book/"+bookId+"/book/return");

  }

}
