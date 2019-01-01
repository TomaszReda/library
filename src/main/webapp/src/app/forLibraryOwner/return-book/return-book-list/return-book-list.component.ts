import {Component, OnInit} from '@angular/core';
import {Book} from "../../../model/book/book.model";
import {ActivatedRoute, Router} from "@angular/router";
import {SearchService} from "../../../service/search.service";
import {PageServiceService} from "../../../service/page-service.service";
import {BookService} from "../../../service/book.service";
import {BookRequestSearch} from "../../../model/book/book.request";

@Component({
  selector: 'app-return-book-list',
  templateUrl: './return-book-list.component.html',
  styleUrls: ['./return-book-list.component.css']
})
export class ReturnBookListComponent implements OnInit {

  public bookList: Array<Book> = new Array<Book>();

  public currentyPage = 0;

  public pageNumber = [];

  constructor(private router: Router, private searchService: SearchService, private pageService: PageServiceService, private bookService: BookService, private activated: ActivatedRoute) {
  }

  ngOnInit() {
    this.currentyPage = 0;
    this.initSearchBookedBook();
  }

  initSearchBookedBook() {
    this.searchService.searchUserBookedBookById(localStorage.getItem("userId"), 0, 10).subscribe((x: BookRequestSearch) => {
      this.bookList = x.content;
      this.pageNumber = this.pageService.returnpages(this.currentyPage + 1, x.totalPages);
      if (x.totalElements === 0) {
        this.pageNumber = null;
      }
    })
  }

  details(bookId) {
    localStorage.setItem("deitalsGeneralSearch", null);
    localStorage.setItem("detailsReservSearch", "true");
    localStorage.setItem("detailsForCasualBookedUser", "true")
    this.router.navigate(["/search/book/" + bookId])
  }

  returns(bookId) {
    this.bookService.bookReturn(bookId).subscribe(x => {
      this.initSearchBookedBook();
    });
    this.initSearchBookedBook();
    let id = this.activated.snapshot.paramMap.get("userId")
    this.router.navigate(["/return/book/user/" + id]);

  }


  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.initSearchBookedBook();

  }


  nextt() {
    this.currentyPage = this.currentyPage + 1;
    this.initSearchBookedBook();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.initSearchBookedBook();
  }
}
