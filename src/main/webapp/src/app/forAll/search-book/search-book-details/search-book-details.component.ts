import {Component, OnInit, ViewChild} from '@angular/core';
import {BookDetailsForCasualUser} from "../../../model/book/book.details.for.casual.user";
import {BookService} from "../../../service/book.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MapServiceService} from "../../../service/map-service.service";
import {NgForm} from "@angular/forms";
import {AuthService} from "../../../service/auth.service";
import {NotificationsService} from "../../../service/notifications.service";

declare var ol: any;

@Component({
  selector: 'app-search-book-details',
  templateUrl: './search-book-details.component.html',
  styleUrls: ['./search-book-details.component.css']
})
export class SearchBookDetailsComponent implements OnInit {

  public errors;

  @ViewChild("quantForm")
  public quantForm: NgForm;

  public book: BookDetailsForCasualUser = new BookDetailsForCasualUser();

  constructor(private notificationService: NotificationsService, private router: Router, private bookService: BookService, private activatedRouter: ActivatedRoute, private  mapService: MapServiceService, public authService: AuthService) {
  }

  ngOnInit() {
    this.errors = null;
    this.quantForm.value.quant = 1;
    this.initializeDetails();
    this.mapService.initialize_map();
  }

  initializeDetails() {
    this.bookService.getDetailsForCasualUser(this.activatedRouter.snapshot.paramMap.get("bookId")).subscribe((x: BookDetailsForCasualUser) => {
      this.book = x;
      this.book.deitalsGeneralSearch = localStorage.getItem("deitalsGeneralSearch");
      this.book.detailsReservSearch = localStorage.getItem("detailsReservSearch");
      this.book.detailsForCasualBookedUser = localStorage.getItem("detailsForCasualBookedUser");
      this.errors = null;
      this.mapService.mapLat = this.book.latitude;
      this.mapService.mapLng = this.book.longitude;
      this.mapService.add_map_point(this.mapService.mapLat, this.mapService.mapLng);
    });

  }


  reservBook(bookId) {
    this.errors = null;
    this.bookService.reservBook(bookId, this.quantForm.value.quant).subscribe(x => {
      if (this.authService.connected) {
        this.authService.sendNotification();
      }

        this.router.navigate(["myReserv"]);
        this.notificationService.getUnreadNotificationGet().subscribe(
          (x: number) => {
            this.authService.unreadNotification = x;
          }
        );
      }, error1 => {
        this.errors = error1.error;
      }
    )
  }


  deleteReserv(bookId) {
    this.errors = null;
    this.bookService.deleteReservBook(bookId).subscribe(x => {
      if (this.authService.connected == true) {
        this.authService.sendNotification();
      }
      this.router.navigate(["myReserv"]);
    });
    this.notificationService.getUnreadNotificationGet().subscribe(
      (x: number) => {
        this.authService.unreadNotification = x;
      }
    )
  }


}
