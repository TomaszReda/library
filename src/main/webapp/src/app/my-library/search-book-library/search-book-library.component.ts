import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {LibraryPageRequest} from "../../model/page/library.page.request";
import {Book} from "../../model/book/book.model";

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

  constructor() {
  }

  ngOnInit() {
  }


  onSubmit() {
  }

  keyDown() {
    if (this.seachForm['word'].length >= 3) {
    }
  }

}
