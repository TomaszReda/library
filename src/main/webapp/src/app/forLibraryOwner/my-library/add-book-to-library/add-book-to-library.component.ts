import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {LibraryService} from "../../../service/library.service";
import {BookService} from "../../../service/book.service";

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

  public bookCategory = ['Fantasy', 'Biografie/Autobiografie', 'Młodzieżowa', 'Naukowa', 'Sportowa', 'Bajka', 'Historyczna', 'Horror', 'Przygodowa', 'Inna'];

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
      isbn: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required]),
      bookCategory: new FormControl(this.bookCategory[0]),
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
        this.submit = false;
        this.initForms();
      }, error1 => {

        let tmp = this.formAddBook.value.date;
        if (tmp.length > 10) {
          this.badAdd = "Błedny format daty";
        } else {
          this.badAdd = "Wystapił bład podczas dodowania książek!";
        }
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
