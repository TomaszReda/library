import {Component, OnInit} from '@angular/core';
import {ReservService} from "../../service/reserv.service";
import {SearchService} from "../../service/search.service";

@Component({
  selector: 'app-reserv-book-list',
  templateUrl: './reserv-book-list.component.html',
  styleUrls: ['./reserv-book-list.component.css']
})
export class ReservBookListComponent implements OnInit {

  constructor(private searchService: SearchService) {
  }

  ngOnInit() {
    this.searchService.searchUserReservBook(localStorage.getItem("userId"), 0, 10).subscribe(x => {
      console.log(x);
      }
    )
  }

}
