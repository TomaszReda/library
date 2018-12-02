import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-book-to-library',
  templateUrl: './add-book-to-library.component.html',
  styleUrls: ['./add-book-to-library.component.css']
})
export class AddBookToLibraryComponent implements OnInit {

  public formAddBook: FormGroup;

  public submit = false;

  constructor() {
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
    this.submit = true;
  }

  reset() {
    this.initForms();
    this.submit = false;
  }

}
