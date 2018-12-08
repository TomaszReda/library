import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class NoPharmacyOwnerGuardServiceService implements CanActivate{

  private url: string = "http://localhost:8080/api/";

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(this.authService.user==null){
      return true;
    }

    for (let i = 0; i < this.authService.user.userRoles.length; i++) {
      if (this.authService.user.userRoles[i].userRole === "LIBRARY_OWNER") {
        this.router.navigate(["/home"]);
        return false
      }
    }

    return true


  }
}
