import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Book} from "../../model/book/book.model";
import {SearchService} from "../../service/search.service";
import {BookRequestSearch} from "../../model/book/book.request";
import {PageServiceService} from "../../service/page-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search-book-library',
  templateUrl: './search-book-library.component.html',
  styleUrls: ['./search-book-library.component.css']
})
export class SearchBookLibraryComponent implements OnInit {

  @ViewChild("search")
  public seachForm: NgForm;

  public bookPageList: Array<Book> = new Array<Book>();

  public currentyPage = 0;

  public pageNumber = [];

  constructor(private searchService: SearchService, private pageService: PageServiceService,private router:Router) {
  }

  ngOnInit() {
    this.resultSearch();
  }


  onSubmit() {
    if (this.seachForm.invalid)
      this.currentyPage = 0;

    this.resultSearch();
  }

  keyDown() {
    if (this.seachForm.invalid)
      this.currentyPage = 0;

    this.resultSearch();

  }

  details(bookId) {
    localStorage.setItem("bookId",bookId);
    this.router.navigate(["/myLibrary/library/book/"+bookId+"/details"]);
  }

  resultSearch() {
    let word = this.seachForm.value.word;
    if (word == null) {
      word = "";
    }
    this.searchService.search(word, this.currentyPage, 10).subscribe((x: BookRequestSearch) => {
      this.bookPageList = x.content;
      this.pageNumber = this.pageService.returnpages(this.currentyPage, x.totalPages);
    });


  }

  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.resultSearch();
  }


  next() {
    this.currentyPage = this.currentyPage + 1;
    this.resultSearch();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.resultSearch();
  }


}
