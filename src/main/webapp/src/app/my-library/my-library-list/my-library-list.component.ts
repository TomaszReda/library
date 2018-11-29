import { Component, OnInit } from '@angular/core';
import {LibraryPageRequest} from "../../model/page/library.page.request";
import {LibraryService} from "../../service/library.service";
import {PageRequest} from "../../model/page/page.request";

@Component({
  selector: 'app-my-library-list',
  templateUrl: './my-library-list.component.html',
  styleUrls: ['./my-library-list.component.css']
})
export class MyLibraryListComponent implements OnInit {

  public libraryPageList: Array<LibraryPageRequest>;

  constructor(private libraryService: LibraryService) {
  }

  ngOnInit() {
    this.libraryService.gettAllLibrary(0, 10).subscribe((x: PageRequest) => {
      this.libraryPageList = x.content;
    })
  }

}
