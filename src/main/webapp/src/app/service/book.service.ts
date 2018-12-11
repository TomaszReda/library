import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private  http: HttpClient) {
  }

  private url: string = 'http://localhost:8080/api';

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

}
