import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {LibraryService} from "../../service/library.service";
import {BookService} from "../../service/book.service";

@Component({
  selector: 'app-add-book-to-library',
  templateUrl: './add-book-to-library.component.html',
  styleUrls: ['./add-book-to-library.component.css']
})
export class AddBookToLibraryComponent implements OnInit {

  public formAddBook: FormGroup;

  public submit = false;

  public succesAdd = null;

  public badAdd = null;

  constructor(private  bookSerice: BookService) {
  }

  ngOnInit() {
    this.initForms();
  }

  initForms() {
    this.formAddBook = new FormGroup({
      author: new FormControl(null, [Validators.required]),
      title: new FormControl(null, [Validators.required]),
      publisher: new FormControl(null, [Validators.required]),
      date: new FormControl(null, [Validators.required]),
      ISBN: new FormControl(null, [Validators.required]),
      quant: new FormControl(null, [Validators.required, Validators.min(1)]),
    })
  }


  addBooks() {
    this.succesAdd = null;
    this.badAdd = null;
    if (this.formAddBook.valid) {
      let object = this.formAddBook.getRawValue();
      object.libraryId = localStorage.getItem('libraryClickId');
      this.bookSerice.addBook(object).subscribe(x => {
        this.succesAdd = "Pomyslnie dodano ksiązki!";
      }, error1 => {
        this.badAdd = "Wystapił bład podczas dodowania książek!"
      });
    }
    this.submit = true;
  }

  reset() {
    this.initForms();
    this.submit = false;
    this.succesAdd = null;
    this.badAdd = null;
  }

}
