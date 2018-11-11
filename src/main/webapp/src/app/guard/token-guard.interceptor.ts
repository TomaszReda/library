import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TokenGuardInterceptor implements HttpInterceptor{

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): any {

    if(localStorage.getItem('tokenID'))
    {
      const reqClone=req.clone({headers: new HttpHeaders().set("x-auth-token",localStorage.getItem('tokenID'))});
      return next.handle(reqClone);
    }
    else {
      return next.handle(req);
    }
  }
}
