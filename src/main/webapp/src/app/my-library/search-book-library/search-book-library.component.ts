import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {LibraryPageRequest} from "../../model/page/library.page.request";
import {Book} from "../../model/book/book.model";
import {SearchService} from "../../service/search.service";
import {BookRequestSearch} from "../../model/book/book.request";
import {LibraryService} from "../../service/library.service";
import {PageServiceService} from "../../service/page-service.service";

@Component({
  selector: 'app-search-book-library',
  templateUrl: './search-book-library.component.html',
  styleUrls: ['./search-book-library.component.css']
})
export class SearchBookLibraryComponent implements OnInit {

  @ViewChild("search")
  public seachForm: NgForm;

  public bookPageList: Array<Book>;

  public currentyPage = 0;

  public pageNumber = [];

  constructor(private searchService: SearchService, private pageService: PageServiceService) {
  }

  ngOnInit() {
   this.resultSearch();
  }


  onSubmit() {
    this.resultSearch();
  }

  keyDown() {
    this.resultSearch();

  }

  resultSearch(){
    this.bookPageList = null;

      let word = this.seachForm.value.word;
      if(word==null){
        word="";
      }
      this.searchService.search(word, this.currentyPage, 10).subscribe((x: BookRequestSearch) => {
        this.bookPageList = x.content;
        this.pageNumber = this.pageService.returnpages(this.currentyPage, x.totalPages);
        if (x.totalElements === 1) {
          this.pageNumber = null;
        }
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
