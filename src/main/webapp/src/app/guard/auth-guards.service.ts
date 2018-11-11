import {Injectable} from '@angular/core';
import {CanLoad, Route, Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardsService implements CanLoad {

  constructor(private router: Router) {
  }

  canLoad(route: Route): boolean {
    if (localStorage.getItem("tokenID"))
      return true

    this.router.navigate(["/home"]);

    return false;
  }
}
