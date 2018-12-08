import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Book} from "../../model/book/book.model";
import {SearchRequest} from "../../model/search/search.request";
import {SearchService} from "../../service/search.service";
import {PageServiceService} from "../../service/page-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search-book-list',
  templateUrl: './search-book-list.component.html',
  styleUrls: ['./search-book-list.component.css']
})
export class SearchBookListComponent implements OnInit {

  public seachForm: SearchRequest = new SearchRequest();

  public bookPageList: Array<Book> = new Array<Book>();

  public currentyPage = 0;

  public pageNumber = []

  constructor(private searchService: SearchService, private pageService: PageServiceService, private router: Router) {

  }

  ngOnInit() {
  }

  onSubmit() {
  }

  keyDown() {
  }

  details(bookId) {
    localStorage.setItem("bookId", bookId);
  }

  next() {
    this.currentyPage += 1;
  }

  changePage(i) {
    this.currentyPage = i;
  }

  previous() {
    this.currentyPage -= 1;
  }

}


