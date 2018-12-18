import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class LibraryService {

  constructor(private http: HttpClient) {
  }

  private url: string = environment.url;


  addLibrary(libraryRequest) {

    return this.http.post(this.url + '/add/library', libraryRequest);
  }

  updateLibrary(libraryRequest) {
    return this.http.put(this.url + '/update/library', libraryRequest);
  }


  gettAllLibrary(page, size) {
    let params = new HttpParams().append('page', page).append("size", size);
    return this.http.get(this.url + '/get/library/list', {params: params});
  }

  getLibraryDeitals(libraryId) {

    return this.http.get(this.url + "/get/library/" + libraryId);
  }

}
