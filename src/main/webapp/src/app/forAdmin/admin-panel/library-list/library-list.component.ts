import {Component, OnInit} from '@angular/core';
import {AdminService} from "../../../service/admin.service";
import {Library} from "../../../model/library/library.model";
import {LibraryRequestSearch} from "../../../model/library/library.request";

@Component({
  selector: 'app-library-list',
  templateUrl: './library-list.component.html',
  styleUrls: ['./library-list.component.css']
})
export class LibraryListComponent implements OnInit {

  public libraryList: Array<Library>;

  constructor(private adminService: AdminService) {
  }

  ngOnInit() {
    this.adminService.getAllLibrary(0, 10).subscribe((x: LibraryRequestSearch) => {
      this.libraryList = x.content;
    });
  }

}
