import {Component, OnInit} from '@angular/core';
import {LibraryService} from "../service/library.service";

@Component({
  selector: 'app-my-library',
  templateUrl: './my-library.component.html',
  styleUrls: ['./my-library.component.css']
})
export class MyLibraryComponent implements OnInit {

  constructor(private libraryService: LibraryService) {
  }

  ngOnInit() {
    this.libraryService.gettAllLibrary().subscribe(x => {
      console.log(x[0]);
  })
  }

}
