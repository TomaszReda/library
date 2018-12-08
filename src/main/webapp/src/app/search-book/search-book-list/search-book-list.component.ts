import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Book} from "../../model/book/book.model";
import {SearchRequest} from "../../model/search/search.request";
import {SearchService} from "../../service/search.service";
import {PageServiceService} from "../../service/page-service.service";
import {Router} from "@angular/router";
import {BookRequestSearch} from "../../model/book/book.request";

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
    this.seachForm.word="";
    this.searchBook();
  }

  searchBook(){
    this.searchService.searchCasualUser(this.seachForm.word, this.currentyPage, 10).subscribe((x:BookRequestSearch) => {
      this.bookPageList=x.content;
      this.pageNumber=this.pageService.returnpages(this.currentyPage,x.totalPages);
    })
  }

  onSubmit() {
    this.searchBook();
  }

  keyDown() {
    this.searchBook();
  }

  details(bookId) {
    console.log(bookId);
  this.router.navigate(["/search/book/"+bookId])
  }

  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.searchBook();
  }


  next() {
    this.currentyPage = this.currentyPage + 1;
    this.searchBook();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.searchBook();
  }

}


