import {Component, OnInit} from '@angular/core';
import {AdminService} from "../../../service/admin.service";
import {Library} from "../../../model/library/library.model";
import {LibraryRequestSearch} from "../../../model/library/library.request";
import {PageServiceService} from "../../../service/page-service.service";
import {SearchRequest} from "../../../model/search/search.request";
import {BookRequestSearch} from "../../../model/book/book.request";

@Component({
  selector: 'app-library-list',
  templateUrl: './library-list.component.html',
  styleUrls: ['./library-list.component.css']
})
export class LibraryListComponent implements OnInit {

  public seachForm: SearchRequest = new SearchRequest()

  public libraryList: Array<Library>;

  public currentyPage = 0;

  public pageNumber = []

  constructor(private adminService: AdminService, private pageService: PageServiceService) {
  }

  ngOnInit() {
    this.seachForm.word = "";
    this.initLibraryList();
  }

  initLibraryList() {
    this.adminService.getAllLibrary(this.currentyPage, 10).subscribe((x: LibraryRequestSearch) => {
      this.libraryList = x.content;
      this.pageNumber = this.pageService.returnpages(this.currentyPage, x.totalPages);

    });
  }

  keyDown() {
    this.searchLibrary();
  }
  onSubmit() {
    this.searchLibrary();
  }
  searchLibrary() {
    this.adminService.getSearchLibrary(this.seachForm.word, this.currentyPage, 10).subscribe((x: BookRequestSearch) => {
      this.libraryList = x.content;
      this.pageNumber = this.pageService.returnpages(this.currentyPage, x.totalPages);
    })
  }

  changePage(currentyPage) {
    this.currentyPage = currentyPage - 1;
    this.initLibraryList();
  }

  next() {
    this.currentyPage = this.currentyPage + 1;
    this.initLibraryList();
  }

  previous() {
    this.currentyPage = this.currentyPage - 1;
    this.initLibraryList();
  }
}
