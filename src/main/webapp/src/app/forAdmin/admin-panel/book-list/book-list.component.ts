import {Component, OnInit} from '@angular/core';
import {Book} from "../../../model/book/book.model";
import {AdminService} from "../../../service/admin.service";
import {BookRequestSearch} from "../../../model/book/book.request";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  public bookList: Array<Book>;

  constructor(private  adminService: AdminService) {
  }

  ngOnInit() {
    this.adminService.getAllBook(0, 10).subscribe((x: BookRequestSearch) => {
      this.bookList = x.content;
    })
  }

}
