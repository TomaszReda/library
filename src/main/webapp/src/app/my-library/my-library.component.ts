import {Component, OnInit} from '@angular/core';
import {LibraryService} from "../service/library.service";
import {PageRequest} from "../model/page/page.request";
import {LibraryPageRequest} from "../model/page/library.page.request";

@Component({
  selector: 'app-my-library',
  templateUrl: './my-library.component.html',
  styleUrls: ['./my-library.component.css']
})
export class MyLibraryComponent implements OnInit {

  public libraryPageList: Array<LibraryPageRequest>;

  constructor(private libraryService: LibraryService) {
  }

  ngOnInit() {
    this.libraryService.gettAllLibrary(0, 10).subscribe((x: PageRequest) => {
      this.libraryPageList = x.content;
    })
  }

}
