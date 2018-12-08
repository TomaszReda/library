import { Component, OnInit } from '@angular/core';
import {Book} from "../../model/book/book.model";

@Component({
  selector: 'app-search-book-details',
  templateUrl: './search-book-details.component.html',
  styleUrls: ['./search-book-details.component.css']
})
export class SearchBookDetailsComponent implements OnInit {

  public book:Book=new Book();
  constructor() { }

  ngOnInit() {
  }

}
