import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../service/auth.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class PharmacyOwnerGuardService implements CanActivate {

  private url: string = environment.url;

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(this.authService.user==null){
      this.router.navigate(["/home"]);
      return false;
    }
    for (let i = 0; i < this.authService.user.userRoles.length; i++) {
      if (this.authService.user.userRoles[i].userRole === "LIBRARY_OWNER") {
        return true;
      }
    }

    this.router.navigate(["/home"]);
    return false;


  }

}
