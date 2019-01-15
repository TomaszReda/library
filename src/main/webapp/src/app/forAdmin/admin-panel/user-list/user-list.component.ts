import {Component, OnInit} from '@angular/core';
import {AdminService} from "../../../service/admin.service";
import {User} from "../../../model/user/user.model";
import {UserRequestSearch} from "../../../model/user/user.request";
import {PageServiceService} from "../../../service/page-service.service";
import {BookRequestSearch} from "../../../model/book/book.request";
import {SearchRequest} from "../../../model/search/search.request";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  public seachForm: SearchRequest = new SearchRequest()

  public userList: Array<User>;

  public currentyPage = 0;

  public pageNumber = []

  constructor(private  adminService: AdminService, private pageService: PageServiceService) {
  }


  ngOnInit() {
    this.seachForm.word = "";
    this.initUserList();
  }

  initUserList() {
    this.adminService.getAllUser(this.currentyPage, 10).subscribe((x: UserRequestSearch) => {
      this.userList = x.content;
      this.pageNumber = this.pageService.returnpages10(this.currentyPage, x.totalPages);

    })

  }

  keyDown() {
    this.searchUser();
  }
  onSubmit() {
    this.searchUser();
  }
  searchUser() {
    this.adminService.getSearchUser(this.seachForm.word, this.currentyPage, 10).subscribe((x: UserRequestSearch) => {
      this.userList = x.content;
      this.pageNumber = this.pageService.returnpages10(this.currentyPage, x.totalPages);
    })
  }

  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.initUserList();
  }

  next() {
    this.currentyPage = this.currentyPage + 1;
    this.initUserList();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.initUserList();
  }
}


