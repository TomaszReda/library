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
    return this.http.post(this.url + "/book/add", bookRequest,{observe: 'response'});
  }
}
