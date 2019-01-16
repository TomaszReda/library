import {Component, OnInit} from '@angular/core';
import {ReservService} from "../../../service/reserv.service";
import {SearchService} from "../../../service/search.service";
import {Book} from "../../../model/book/book.model";
import {BookRequestSearch} from "../../../model/book/book.request";
import {PageServiceService} from "../../../service/page-service.service";
import {BookService} from "../../../service/book.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-reserv-book-list',
  templateUrl: './reserv-book-list.component.html',
  styleUrls: ['./reserv-book-list.component.css']
})
export class ReservBookListComponent implements OnInit {

  public bookList: Array<Book> = new Array<Book>();

  public currentyPage = 0;

  public pageNumber = [];

  constructor(private router: Router, private searchService: SearchService, private pageService: PageServiceService, private bookService: BookService, private activated: ActivatedRoute) {
  }

  ngOnInit() {
    this.currentyPage = 0;
    this.initSearchReservBook();
  }

  initSearchReservBook() {
    this.searchService.searchUserReservBook(localStorage.getItem("userId"), 0, 10).subscribe((x: BookRequestSearch) => {
      this.bookList = x.content;
      this.pageNumber = this.pageService.returnpages10(this.currentyPage + 1, x.totalPages);
      if (x.totalElements === 0) {
        this.pageNumber = null;
      }
    })
  }

  details(bookId){
    localStorage.setItem("deitalsGeneralSearch",null);
    localStorage.setItem("detailsReservSearch","true");
    localStorage.setItem("detailsForCasualBookedUser","true")
    this.router.navigate(["/search/book/"+bookId])
  }

  akcept(bookId) {
    this.bookService.confirmReserv(bookId).subscribe(x => {
      let id = this.activated.snapshot.paramMap.get("userId")
      this.router.navigate(["/reserv/book/user/" + id]);
      this.initSearchReservBook();
      console.log("a");
    });
  }

  remove(bookId) {
    this.bookService.deleteReserv(bookId).subscribe(x => {
      let id = this.activated.snapshot.paramMap.get("userId")
      this.router.navigate(["/reserv/book/user/" + id]);
      this.initSearchReservBook();

    });
  }

  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.initSearchReservBook();

  }


  nextt() {
    this.currentyPage = this.currentyPage + 1;
    this.initSearchReservBook();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.initSearchReservBook();
  }
}
