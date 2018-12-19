import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../service/search.service";
import {HttpClient} from "@angular/common/http";
import {SearchRequest} from "../../model/search/search.request";
import {Book} from "../../model/book/book.model";
import {PageRequest} from "../../model/page/page.request";
import {BookRequestSearch} from "../../model/book/book.request";
import {PageServiceService} from "../../service/page-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-reserv-book-list',
  templateUrl: './my-reserv-book-list.component.html',
  styleUrls: ['./my-reserv-book-list.component.css']
})
export class MyReservBookListComponent implements OnInit {

  public seachForm: SearchRequest = new SearchRequest();

  public bookPageList: Array<Book> = new Array<Book>();

  public currentyPage = 0;

  public pageNumber = []

  constructor(private  searchService: SearchService, private pageService: PageServiceService, private router: Router) {
  }

  ngOnInit() {
    this.initSearchResult();
  }

  initSearchResult() {
    this.seachForm.word="";
    this.searchService.searchReservBook(this.seachForm.word, this.currentyPage, 10).subscribe((x:BookRequestSearch) => {
      this.bookPageList=x.content;
      this.pageNumber=this.pageService.returnpages(this.currentyPage,x.totalPages);
    })

  }


  onSubmit() {
    this.initSearchResult();
  }

  keyDown() {
    this.initSearchResult();
  }

  details(bookId) {
    localStorage.setItem("deitalsGeneralSearch",null);
    localStorage.setItem("detailsForCasualBookedUser",null)
    localStorage.setItem("detailsReservSearch","true");
    this.router.navigate(["search/book/"+bookId])
  }

  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.initSearchResult();
  }


  next() {
    this.currentyPage = this.currentyPage + 1;
    this.initSearchResult();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.initSearchResult();
  }


}
