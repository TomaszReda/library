import {Component, OnInit} from '@angular/core';
import {BookDetailsForCasualUser} from "../../model/book/book.details.for.casual.user";
import {BookService} from "../../service/book.service";
import {ActivatedRoute} from "@angular/router";
import {MapServiceService} from "../../service/map-service.service";
declare var ol: any;

@Component({
  selector: 'app-search-book-details',
  templateUrl: './search-book-details.component.html',
  styleUrls: ['./search-book-details.component.css']
})
export class SearchBookDetailsComponent implements OnInit {

  public map: any;
  public vectorLayer;

  public book: BookDetailsForCasualUser = new BookDetailsForCasualUser();

  constructor(private bookService: BookService, private activatedRouter: ActivatedRoute,private  mapService:MapServiceService) {
  }

  ngOnInit() {
    this.initializeDetails();
    this.mapService.initialize_map();
    this.map=this.mapService.map;
  }

  initializeDetails() {
    this.bookService.getDetailsForCasualUser(this.activatedRouter.snapshot.paramMap.get("bookId")).subscribe((x:BookDetailsForCasualUser) => {
      this.book=x;
      this.mapService.mapLat=this.book.latitude;
      this.mapService.mapLng=this.book.longitude;
      this.mapService.add_map_point(this.mapService.mapLat,this.mapService.mapLng);
    });

  }







}
