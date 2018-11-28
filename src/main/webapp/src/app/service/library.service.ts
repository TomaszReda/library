import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

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


  gettAllLibrary(){
    let params=new HttpParams().append('page','0').append("size",'10');
    return this.http.get(this.url+'/get/library/list',{params: params});
  }

}
