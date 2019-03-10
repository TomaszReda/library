import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../service/auth.service";

;

@Injectable({
  providedIn: 'root'
})
export class AdminGuardService implements CanLoad, CanActivate {

  constructor(private authService: AuthService, private router: Router) {
  }

  canLoad(route: Route): boolean {
    if (this.authService.user == null) {
      this.router.navigate(["/home"]);
      return false;
    }
    for (let i = 0; i < this.authService.user.userRoles.length; i++) {
      if (this.authService.user.userRoles[i].userRole === "ADMIN") {
        return true;
      }
    }
    this.router.navigate(["/home"]);
    return false;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.user == null) {
      this.router.navigate(["/home"]);
      return false;
    }
    for (let i = 0; i < this.authService.user.userRoles.length; i++) {
      if (this.authService.user.userRoles[i].userRole === "ADMIN") {
        return true;
      }
    }
    this.router.navigate(["/home"]);
    return false;
  }


}
