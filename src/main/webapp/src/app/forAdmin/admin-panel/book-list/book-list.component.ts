import {Component, OnInit} from '@angular/core';
import {Book} from "../../../model/book/book.model";
import {AdminService} from "../../../service/admin.service";
import {BookRequestSearch} from "../../../model/book/book.request";
import {PageServiceService} from "../../../service/page-service.service";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  public bookList: Array<Book>;

  public currentyPage = 0;

  public pageNumber = []

  constructor(private  adminService: AdminService,private pageService: PageServiceService) {
  }

  ngOnInit() {
   this.initBookList();
  }

  initBookList(){
    this.adminService.getAllBook(this.currentyPage, 10).subscribe((x: BookRequestSearch) => {
      this.bookList = x.content;
      this.pageNumber=this.pageService.returnpages(this.currentyPage,x.totalPages);

    })
  }

  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.initBookList();  }


  next() {
    this.currentyPage = this.currentyPage + 1;
    this.initBookList();  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.initBookList();
  }


}
