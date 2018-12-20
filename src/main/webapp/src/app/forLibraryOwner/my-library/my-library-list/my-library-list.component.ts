import {Component, OnInit} from '@angular/core';
import {LibraryPageRequest} from "../../../model/page/library.page.request";
import {LibraryService} from "../../../service/library.service";
import {PageRequest} from "../../../model/page/page.request";
import {PageServiceService} from "../../../service/page-service.service";

@Component({
  selector: 'app-my-library-list',
  templateUrl: './my-library-list.component.html',
  styleUrls: ['./my-library-list.component.css']
})
export class MyLibraryListComponent implements OnInit {

  public libraryPageList: Array<LibraryPageRequest>;

  public currentyPage = 0;

  public pageNumber = [];

  constructor(private libraryService: LibraryService, private pageService: PageServiceService) {
  }

  ngOnInit() {
    this.currentyPage = 0;
    this.initLibraryList();
  }

  setLibrary(l) {
    localStorage.setItem('libraryId',l);
  }

  initLibraryList() {
    this.libraryService.gettAllLibrary(this.currentyPage, 10).subscribe((x: PageRequest) => {
      this.libraryPageList = x.content;
      this.pageNumber = this.pageService.returnpages(this.currentyPage + 1, x.totalPages);
      if (this.libraryPageList.length === 0) {
        this.pageNumber = null;
      }
    })
  }

  addBook(l) {
    localStorage.setItem("libraryClickId", l);
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
