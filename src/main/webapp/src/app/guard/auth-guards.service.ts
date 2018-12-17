import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../service/auth.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardsService implements CanActivate {

  private url: string = environment.url;

  constructor(private router: Router, private authService: AuthService, private http: HttpClient) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {


    if (localStorage.getItem("tokenID")) {
      this.http.get(this.url+"/tokenValid").subscribe(
        x => {
          return true;
        }, error1 => {

          localStorage.removeItem("tokenID");
          this.router.navigate(["/home"]);
          this.authService.logout();
          return false;
        }
      );
    }

    if (localStorage.getItem("tokenID")) {
      return true;
    }
    else {
      this.router.navigate(["/home"]);
      this.authService.logout();
      return false;
    }


  }


}


