import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../service/auth.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PharmacyOwnerGuardService implements CanActivate {

  private url: string = "http://localhost:8080/api/";

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    for (let i = 0; i < this.authService.user.userRoles.length; i++) {
      if (this.authService.user.userRoles[i].userRole === "LIBRARY_OWNER") {
        return true;
      }
    }

    this.router.navigate(["/home"]);
    return false;


  }

}
