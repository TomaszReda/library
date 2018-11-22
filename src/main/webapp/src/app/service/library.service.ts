import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LibraryService {

  constructor(private http: HttpClient) {
  }

  private url: string = 'http://localhost:8080/api';


  addLibrary(libraryRequest) {

    return this.http.post(this.url + '/add/library', libraryRequest);
  }


}
