import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {User} from "../../model/user/user.model";
import {ReservService} from "../../service/reserv.service";

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

  public errors = null;


  constructor(private reservService: ReservService) {
  }


  ngOnInit() {
    this.submit = false;
  }

  onSubmit() {
    this.errors = null;
    this.submit = true;
    this.reservService.getReservUserDetails(this.email).subscribe(x => {
    this.user=x;
    }, error1 => {
      console.log(error1);
      this.errors = error1.error;
    })
  }

}
