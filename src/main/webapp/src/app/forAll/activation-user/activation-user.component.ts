import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-activation-user',
  templateUrl: './activation-user.component.html',
  styleUrls: ['./activation-user.component.css']
})
export class ActivationUserComponent implements OnInit {

  public succesActivation: string;

  public failActivation: string;

  constructor(public userService: UserService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.init();

  }

  init() {
    this.failActivation = null;
    this.succesActivation = null;
    // this.userService.resetPassword(this.activatedRoute.snapshot.paramMap.get("resetPasswordToken"));


    this.userService.activationUser(this.activatedRoute.snapshot.paramMap.get("activationToken")).subscribe((x:string) => {
      this.succesActivation=x;
    },error1 => {
      this.failActivation=error1.error;
    });


  }





}

