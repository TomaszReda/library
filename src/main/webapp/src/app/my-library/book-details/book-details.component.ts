import {Component, OnInit} from '@angular/core';
import {BookService} from "../../service/book.service";
import {Book} from "../../model/book/book.model";

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  public book: Book;

  constructor(private bookService: BookService) {
  }

  ngOnInit() {
    this.initDetails();
  }

  initDetails() {

    this.bookService.getDetails(localStorage.getItem("bookId")).subscribe(x => {
      this.book = x;
    });

  }


  deleteBook(bookId) {
    this.bookService.deleteBook(localStorage.getItem("bookId"),this.book.quant).subscribe(x => {
    });
  }
}
