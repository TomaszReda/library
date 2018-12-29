import { Component, OnInit } from '@angular/core';
import {Book} from "../../../model/book/book.model";
import {AdminService} from "../../../service/admin.service";
import {BookRequestSearch} from "../../../model/book/book.request";
import {User} from "../../../model/user/user.model";
import {UserRequestSearch} from "../../../model/user/user.request";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  public userList: Array<User>;

  constructor(private  adminService: AdminService) {
  }

  ngOnInit() {
    this.adminService.getAllUser(0, 10).subscribe((x:UserRequestSearch) => {
      this.userList = x.content;
    })
  }
}
