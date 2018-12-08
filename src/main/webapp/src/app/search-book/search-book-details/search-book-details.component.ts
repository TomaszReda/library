import {Component, OnInit} from '@angular/core';
import {BookDetailsForCasualUser} from "../../model/book/book.details.for.casual.user";
import {BookService} from "../../service/book.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-search-book-details',
  templateUrl: './search-book-details.component.html',
  styleUrls: ['./search-book-details.component.css']
})
export class SearchBookDetailsComponent implements OnInit {

  public book: BookDetailsForCasualUser = new BookDetailsForCasualUser();

  constructor(private bookService: BookService, private activatedRouter: ActivatedRoute) {
  }

  ngOnInit() {
    this.initializeDetails();
  }

  initializeDetails() {
    this.bookService.getDetailsForCasualUser(this.activatedRouter.snapshot.paramMap.get("bookId")).subscribe((x:BookDetailsForCasualUser) => {
      this.book=x;
    });
  }

}
