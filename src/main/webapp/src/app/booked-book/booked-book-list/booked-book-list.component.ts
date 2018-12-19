import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from "../../model/user/user.model";
import {NgForm} from "@angular/forms";
import {Book} from "../../model/book/book.model";
import {HttpClient} from "@angular/common/http";
import {SearchService} from "../../service/search.service";
import {BookRequestSearch} from "../../model/book/book.request";
import {PageServiceService} from "../../service/page-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-booked-book-list',
  templateUrl: './booked-book-list.component.html',
  styleUrls: ['./booked-book-list.component.css']
})
export class BookedBookListComponent implements OnInit {


  public bookList: Array<Book> = new Array<Book>();

  public currentyPage = 0;

  public pageNumber = [];


  constructor(private searchService: SearchService,private pageService:PageServiceService,private router:Router) {
  }

  ngOnInit() {
    this.initializeBookedBook();
  }

  initializeBookedBook() {
    this.searchService.searchBookedBook(this.currentyPage, 10).subscribe((x:BookRequestSearch) => {
      this.bookList=x.content;
      this.pageNumber=this.pageService.returnpages(this.currentyPage,x.totalPages);
      if (x.totalElements === 0) {
        this.pageNumber = null;
      }
    })
  }


  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.initializeBookedBook();
  }


  next() {
    this.currentyPage = this.currentyPage + 1;
    this.initializeBookedBook();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.initializeBookedBook();
  }

  details(bookId){
    localStorage.setItem("deitalsGeneralSearch",null);
    localStorage.setItem("detailsReservSearch","true");
    localStorage.setItem("detailsForCasualBookedUser","true")
    this.router.navigate(["/search/book/"+bookId])
  }
}
