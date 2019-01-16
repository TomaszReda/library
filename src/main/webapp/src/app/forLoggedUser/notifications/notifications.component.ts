import {Component, OnInit} from '@angular/core';
import {notification} from "../../model/notification/notification.model";
import {NotificationsService} from "../../service/notifications.service";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user/user.model";
import {NotificationRequest} from "../../model/page/notification.request";
import {PageServiceService} from "../../service/page-service.service";
import {init} from "protractor/built/launcher";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  public notificationList: Array<notification> = new Array<notification>();

  public currentyPage = 0;

  public pageNumber = [];

  constructor(private authService: AuthService, private notificationService: NotificationsService, private userService: UserService, private pageService: PageServiceService) {
  }

  ngOnInit() {
    this.init();
  }

  init() {
    this.userService.getLoggerInfo().subscribe((x: User) => {
      for (let i = 0; i < x.userRoles.length; i++) {
        if (x.userRoles[i].userRole === "LIBRARY_OWNER") {
          this.notificationService.getAllUnreadNotificationForLibraryOwner(this.currentyPage, 5).subscribe((x: NotificationRequest) => {
            this.notificationList = x.content;
            this.pageNumber = this.pageService.returnpages5(this.currentyPage, x.totalPages);
            if (x.totalElements === 0) {
              this.pageNumber = null;
            }
          })
        }
        if (x.userRoles[i].userRole === "CASUAL_USER") {
          this.notificationService.getAllUnreadNotificationForCasualUser(this.currentyPage, 5).subscribe((x: NotificationRequest) => {
            this.notificationList = x.content;
            this.pageNumber = this.pageService.returnpages5(this.currentyPage, x.totalPages);
            if (x.totalElements === 0) {
              this.pageNumber = null;
            }
          })
        }

      }
      this.notificationService.getUnreadNotificationGet().subscribe(
        (x: number) => {
          this.authService.unreadNotification = x;
          console.log("a");
        }
      )
    });

  }


  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.init();
  }


  next() {
    this.currentyPage = this.currentyPage + 1;
    this.init();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.init();
  }

  deleteMessage(messageId) {
    this.notificationService.readNotification(messageId).subscribe(x => {
        this.init();
          }
    )
  }

  readAll() {
    this.notificationService.readAllNotification().subscribe(x => {
      this.init();

    });
  }

}
