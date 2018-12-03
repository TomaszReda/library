import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private url: string = 'http://localhost:8080/api';


  constructor(private httpClient: HttpClient) {
  }

  search( word,page, size) {
    let libraryId = localStorage.getItem("libraryId")
    let params = new HttpParams().append("word", word).append('page', page).append("size", size);
    return this.httpClient.get(this.url + "/search/library/" + libraryId, {params: params});
  }

}
