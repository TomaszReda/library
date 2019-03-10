import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../service/user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-reset-password-message',
  templateUrl: './reset-password-message.component.html',
  styleUrls: ['./reset-password-message.component.css']
})
export class ResetPasswordMessageComponent implements OnInit {

  public succesReset: string;

  public failReset: string;

  constructor(public userService: UserService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.init();
  }

  init() {
    this.failReset = null;
    this.succesReset = null;
    // this.userService.resetPassword(this.activatedRoute.snapshot.paramMap.get("resetPasswordToken"));


    this.userService.resetPassword(this.activatedRoute.snapshot.paramMap.get("resetPasswordToken")).subscribe((x:string) => {
      this.succesReset=x;
    },error1 => {
      this.failReset=error1.error;
    });

  }
}
