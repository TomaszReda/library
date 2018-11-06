import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../service/user.service";
import {User} from "../model/user/user.model";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  public userList: Array<User> = new Array<User>();

  constructor(private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(
      (data:Array<User>)=> {
        this.userList = data;
        console.log(this.userList);
      }
    );

  }

}
