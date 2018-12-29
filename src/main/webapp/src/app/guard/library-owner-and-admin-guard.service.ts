import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class LibraryOwnerAndAdminGuardService implements CanActivate, CanLoad {


  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(this.authService.user==null){
      this.router.navigate(["/home"]);
      return false;
    }
    for (let i = 0; i < this.authService.user.userRoles.length; i++) {
      if (this.authService.user.userRoles[i].userRole === "LIBRARY_OWNER" || this.authService.user.userRoles[i].userRole === "ADMIN") {
        return true;
      }
    }
    this.router.navigate(["/home"]);
    return false;
  }

  canLoad(route: Route): boolean {
    if (this.authService.user == null) {
      this.router.navigate(["/home"]);
      return false;
    }
    for (let i = 0; i < this.authService.user.userRoles.length; i++) {
      if (this.authService.user.userRoles[i].userRole === "LIBRARY_OWNER" || this.authService.user.userRoles[i].userRole === "ADMIN") {
        return true;
      }
    }
    this.router.navigate(["/home"]);
    return false;
  }

}
