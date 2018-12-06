import {Component, OnInit, ViewChild} from '@angular/core';
import {BookService} from "../../service/book.service";
import {Book} from "../../model/book/book.model";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  public book: Book = new Book();

  @ViewChild("quantForm")
  public quantForm: string;

  public isDelete = false;

  public badQuantNumberToDelete = null;

  constructor(private bookService: BookService, private  router: Router) {
  }

  ngOnInit() {
    this.badQuantNumberToDelete = null;
    this.quantForm['quant'] = 1;
    this.initDetails();
  }

  initDetails() {
    this.isDelete = false;
    this.bookService.getDetails(localStorage.getItem("bookId")).subscribe(x => {
      this.book = x;
      console.log(this.book.bookState)

      if (this.book.bookState != 'Dostępna') {
        this.isDelete = true
      }
    });


  }


  deleteBook(bookId) {
    this.badQuantNumberToDelete = null;
    if (this.quantForm['quant'] < 1) {
      this.badQuantNumberToDelete = "Ilosc ksiązek do usuniecia musi wynosic conajmniej 1!";
    } else if (this.book.quant < this.quantForm['quant']) {
      this.badQuantNumberToDelete = "Ilosc ksiązek do usuniecia jest wieksza od ilości posiadanych książek";
    } else {
      this.bookService.deleteBook(localStorage.getItem("bookId"), this.quantForm["quant"]).subscribe(x => {
        this.router.navigate(["/myLibrary/library/search/book"])
      });
    }
  }
}
