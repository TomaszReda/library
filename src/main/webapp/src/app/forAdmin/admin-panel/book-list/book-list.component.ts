import {Component, OnInit} from '@angular/core';
import {Book} from "../../../model/book/book.model";
import {AdminService} from "../../../service/admin.service";
import {BookRequestSearch} from "../../../model/book/book.request";
import {PageServiceService} from "../../../service/page-service.service";
import {Router} from "@angular/router";
import {SearchRequest} from "../../../model/search/search.request";
import {SearchService} from "../../../service/search.service";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  public seachForm: SearchRequest = new SearchRequest()

  public bookList: Array<Book>;

  public currentyPage = 0;

  public pageNumber = []

  constructor(private searchService: SearchService, private router: Router, private  adminService: AdminService, private pageService: PageServiceService) {
  }

  ngOnInit() {
    this.seachForm.word = "";
    this.initBookList();
  }

  searchBook() {
    this.searchService.searchCasualUser(this.seachForm.word, this.currentyPage, 10).subscribe((x: BookRequestSearch) => {
      this.bookList = x.content;
      this.pageNumber = this.pageService.returnpages10(this.currentyPage, x.totalPages);
    })
  }

  onSubmit() {
    this.searchBook();
  }

  keyDown() {
    this.searchBook();
  }

  initBookList(){
    this.adminService.getAllBook(this.currentyPage, 10).subscribe((x: BookRequestSearch) => {
      this.bookList = x.content;
      this.pageNumber=this.pageService.returnpages10(this.currentyPage,x.totalPages);

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

  details(id, bookId) {

      localStorage.setItem("deitalsGeneralSearch",null);
      localStorage.setItem("detailsReservSearch",null);
    if (bookId != null)
      this.router.navigate(["/search/book/"+bookId]);
    else
      this.router.navigate(["/search/book/" + id]);

    }


}
