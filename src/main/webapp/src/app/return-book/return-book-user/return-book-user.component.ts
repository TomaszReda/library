import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from "../../model/user/user.model";
import {NgForm} from "@angular/forms";
import {ReservService} from "../../service/reserv.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-return-book-user',
  templateUrl: './return-book-user.component.html',
  styleUrls: ['./return-book-user.component.css']
})
export class ReturnBookUserComponent implements OnInit {

  public user: User = null;

  @ViewChild("search")
  public reserv: NgForm;

  public email: string;

  public submit = false;

  public errors = null;


  constructor(private reservService: ReservService, private router: Router) {
  }


  ngOnInit() {
    this.submit = false;
  }

  onSubmit() {
    this.errors = null;
    this.submit = true;
    this.reservService.getReservUserDetails(this.email).subscribe(x => {
      this.user = x;
    }, error1 => {
      console.log(error1);
      this.errors = error1.error;
    })
  }

  details() {

    this.router.navigate(["/user/" + this.user.id]);
  }

  reservDetails() {
    localStorage.setItem("userId", this.user.id);
    this.router.navigate(["/return/book/user/" + this.user.id]);
  }


}
