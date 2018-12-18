import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, Router} from "@angular/router";
import {User} from "../model/user/user.model";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  constructor(private router: Router, private activaterdRouter: ActivatedRoute, private userService: UserService) {
  }

  public user: User = new User();

  ngOnInit() {
    this.userService.userInfo(this.activaterdRouter.snapshot.paramMap.get("id")).subscribe(x => {
      this.user = x;
    })
  }

}
