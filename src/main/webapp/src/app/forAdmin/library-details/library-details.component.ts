import {Component, OnInit} from '@angular/core';
import {Library} from "../../model/library/library.model";
import {AdminService} from "../../service/admin.service";
import {ActivatedRoute} from "@angular/router";
import {MapServiceService} from "../../service/map-service.service";

@Component({
  selector: 'app-library-details',
  templateUrl: './library-details.component.html',
  styleUrls: ['./library-details.component.css']
})
export class LibraryDetailsComponent implements OnInit {

  public library: Library;


  constructor(private  mapService: MapServiceService, private adminSerivce: AdminService, private activaterdRouter: ActivatedRoute,) {
  }

  ngOnInit() {
    this.mapService.initialize_map();
    this.initLibraryDetails();
  }

  public initLibraryDetails() {
    this.adminSerivce.getLibraryDetails(this.activaterdRouter.snapshot.paramMap.get("libraryId")).subscribe((x: Library) => {
      this.library = x;
      this.mapService.mapLat = this.library.latitude;
      this.mapService.mapLng = this.library.longitude;
      this.mapService.add_map_point(this.mapService.mapLat, this.mapService.mapLng);
    })
  }

}
