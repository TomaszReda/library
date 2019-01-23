import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ModalComponent} from "angular-custom-modal";
import {Credentials} from "../model/user/Credentials";
import {User} from "../model/user/user.model";
import {FormGroup} from "@angular/forms";
import {UserService} from "./user.service";
import {UserRoles} from "../model/user/user.roles.model";
import {NotificationsService} from "./notifications.service";
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {environment} from "../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public islogin: boolean = false;

  public isLibraryOwner: boolean = false;

  public badLogin: string = null;

  public badRegisterCasual: string = null;

  public badRegisterLibrary: string = null;

  public user: User;

  public pharmacyOwner: boolean = false;

  public admin: boolean = false;

  public casualUser: boolean = false;

  public badResetPassowrdEmail: string = null;

  public sendResetPasswordEmail: string = null;

  public unreadNotification: number;

  private urlWebSocket = environment.webSocketUrl;

  private stompClient = null;

  public connected: boolean = false;


  constructor( private http: HttpClient, private router: Router, private userService: UserService, private notificationService: NotificationsService) {
  }

  url: string = environment.url;

  readUnreadNotification(){
    this.notificationService.getUnreadNotificationGet().subscribe(
      (x: number) => {
        console.log("notification"+x)
        this.unreadNotification = x;
      }
    );
  }

  login(email: string, password: string, modalLogin: ModalComponent) {
    const creditians = {
      email,
      password
    };
    this.http.post(this.url + "/login", creditians).subscribe((x: Credentials) => {
      this.connect();
      this.notificationService.getUnreadNotificationPost(email).subscribe((x:number) => {
        this.unreadNotification=x;
      });

      localStorage.setItem("tokenID", x.token)
      modalLogin.close();
      this.badLogin = null;
      this.islogin = true;
      this.userService.getLoggerInfo().subscribe((x: User) => {
        this.user = x;
        let owner: UserRoles = new UserRoles("LIBRARY_OWNER");
        owner.userRole = "LIBRARY_OWNER";

        let admin: UserRoles = new UserRoles("ADMIN");
        admin.userRole = "ADMIN";

        let casualsUser: UserRoles = new UserRoles("CASUAL_USER");
        casualsUser.userRole = "CASUAL_USER";

        for (let i = 0; i < this.user.userRoles.length; i++) {
          if (this.user.userRoles[i].userRole === "LIBRARY_OWNER") {
            this.pharmacyOwner = true;
          }
          if (this.user.userRoles[i].userRole === "CASUAL_USER") {
            this.casualUser = true;
          }
          if (this.user.userRoles[i].userRole === "ADMIN") {
            this.admin = true;
          }
        }


      })
    }, error1 => {
      localStorage.removeItem("tokenID");
      this.badLogin = error1.error.message;
      this.islogin = false;
    })


  }


  logout() {
    this.router.navigate(["/home"]);
    this.user = null;
    this.pharmacyOwner = null;
    this.casualUser = null;
    this.admin = null;
    this.http.get(this.url + "/logout").subscribe(x => {
      this.disconnect();
      this.user = null;
      this.islogin = false;
      localStorage.removeItem("tokenID");
    })
  }

  registerCasualUser(user: User, form: FormGroup, modalRegister: ModalComponent) {
    this.http.post(this.url + "/registerCasual", user).subscribe(x => {
      modalRegister.close();
      this.badRegisterCasual = null;
      form.reset();
    }, error1 => {
      this.badRegisterCasual = error1.error;
    });
  }

  registerLibraryOwner(user: User, form: FormGroup, modalRegister: ModalComponent) {
    this.http.post(this.url + "/registerOwner", user).subscribe(x => {
      modalRegister.close();
      this.badRegisterLibrary = null;
      form.reset();
    }, error1 => {
      this.badRegisterLibrary = error1.error;
    })
  }

  resetPasswordEmail(email, forgettingPasswordForm) {
    this.http.post(this.url + "/user/send/reset/password/email", email).subscribe(x => {
      this.badResetPassowrdEmail = null;
      this.sendResetPasswordEmail = "Wysłano na podany email link do resetu hasła!"
    }, error1 => {
      this.badResetPassowrdEmail = error1.error;
      this.sendResetPasswordEmail = null;
    })

  }


  connect() {
    this.connected = false;
    const socket = new SockJS(this.urlWebSocket + 'websockets');
    if (socket.connected == false) {

    } else {
      this.connected = true;
      this.stompClient = Stomp.over(socket);
      const _this = this;
      this.stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        _this.stompClient.subscribe('/app/notification', function (hello) {
          console.log("connector");
          _this.readUnreadNotification();
          console.log("connector");

        });
      });
    }

  }

  disconnect() {
    this.connected = false;

    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected!');
  }

  sendNotification() {
    this.stompClient.send(
      '/notification',
      {},
      JSON.stringify({'name': "test"})
    );
  }


}
