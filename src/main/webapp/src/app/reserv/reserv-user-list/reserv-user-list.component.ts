import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {User} from "../../model/user/user.model";

@Component({
  selector: 'app-reserv-user-list',
  templateUrl: './reserv-user-list.component.html',
  styleUrls: ['./reserv-user-list.component.css']
})
export class ReservUserListComponent implements OnInit {

  public user: User = null;

  @ViewChild("search")
  public reserv: NgForm;

  public email: string;

  public submit = false;

  constructor() {
  }


  ngOnInit() {
    this.submit = false;
  }

  onSubmit() {
    this.submit = true;
    console.log(this.email+"");
  }

}
