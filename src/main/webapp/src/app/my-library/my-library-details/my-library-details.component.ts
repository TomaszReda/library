import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpParams} from "@angular/common/http";
import {LibraryService} from "../../service/library.service";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute} from "@angular/router";
import {Library} from "../../model/library/library.model";
import {MapServiceService} from "../../service/map-service.service";

declare var ol: any;

@Component({
  selector: 'app-my-library-details',
  templateUrl: './my-library-details.component.html',
  styleUrls: ['./my-library-details.component.css']
})
export class MyLibraryDetailsComponent implements OnInit {

  public map: any;
  public mapLat: string = '52.2051';
  public mapLng: string = '21.0158';
  public mapDefaultZoom = 10;
  public vectorLayer;
  success = null;

  errors = null;

  notFindInMap = null;


  submitted = false;

  submittSearch = false;


  url = 'https://nominatim.openstreetmap.org/search';

  public forModifyLibrary: FormGroup;

  constructor(private http: HttpClient, private libraryService: LibraryService, private auth: AuthService, private router: ActivatedRoute, private mapService: MapServiceService) {

  }


  ngOnInit(): void {
    this.success = null;
    this.errors = null;
    this.notFindInMap = null;
    this.submitted = false;
    this.submittSearch = false;

    this.initDetails();
    this.mapService.mapLng = this.mapLng;
    this.mapService.mapLat = this.mapLat;
    this.mapService.initialize_map();

  }


  initDetails() {
    this.submitted = false;
    this.submittSearch = false;
    this.initialize_form();


  }


  initialize_form() {
    this.forModifyLibrary = new FormGroup({
      name: new FormControl(null, [Validators.required]),
      street: new FormControl(null),
      number: new FormControl(null, [Validators.required]),
      local: new FormControl(null),
      postalCode: new FormControl(null, [Validators.required]),
      city: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required, Validators.email]),
      latitude: new FormControl(null),
      longitude: new FormControl(null),
    });


    this.libraryService.getLibraryDeitals(this.router.snapshot.paramMap.get("libraryId")).subscribe((x: Library) => {
        this.forModifyLibrary = new FormGroup({
          name: new FormControl(x.name, [Validators.required]),
          street: new FormControl(x.street),
          number: new FormControl(x.number, [Validators.required]),
          local: new FormControl(x.local),
          postalCode: new FormControl(x.postalCode, [Validators.required]),
          city: new FormControl(x.city, [Validators.required]),
          email: new FormControl(x.email, [Validators.required, Validators.email]),
          latitude: new FormControl(x.latitude),
          longitude: new FormControl(x.longitude),
        });
        this.searchOnMap();
        this.mapService.add_map_point(x.latitude, x.longitude);
      }
    )

  }


  searchOnMap() {
    this.errors = null;
    this.notFindInMap = null;
    let adress = this.forModifyLibrary.value.city;
    if (this.forModifyLibrary.value.street) {
      adress += '+' + this.forModifyLibrary.value.street;
    }
    if (this.forModifyLibrary.value.number) {
      adress += '+' + this.forModifyLibrary.value.number;
    }
    adress += '+' + this.forModifyLibrary.value.postalCode;


    let params = new HttpParams().set('q', adress).append('format', 'json');
    this.http.get(this.url, {params: params}).subscribe(
      x => {

        if (x[0]) {
          this.submittSearch=false;
          let latitude = x[0].lat;
          let longitude = x[0].lon;

          this.mapService.mapLat = latitude;
          this.mapService.mapLng = longitude;

          this.mapService.setCenter();
          this.mapService.add_map_point(latitude, longitude);
          this.notFindInMap = null;

        } else {
          this.notFindInMap = "Nie ma takiej lokalizacji! Upewnij sie czy podajesz poprawny adres !";
          this.mapService.mapLat = '52.2051';
          this.mapService.mapLng = '21.0158';
          this.mapService.setCenter();
          if (this.vectorLayer) {
            this.map.removeLayer(this.vectorLayer);
          }
        }

      }
    );

  }

  keyUp() {
    this.submittSearch = true;
  }

  editLibrary() {

    this.notFindInMap = null;
    this.submitted = true;
    this.success=null;
    this.errors=null;

    if (this.submittSearch === true ) {
      this.errors = "Zmieniłes cos w adresie. Musisz zaznaczyć na mapie lokalizacje";
      return;
    }

    if (!this.forModifyLibrary.valid) {
      return "blad";
    }


    const object = this.forModifyLibrary.getRawValue();

    object.latitude = this.mapService.mapLat;
    object.longitude = this.mapService.mapLng;
    object.userID = this.auth.user.id;
    object.libraryID = this.router.snapshot.paramMap.get("libraryId");

    this.libraryService.updateLibrary(object).subscribe(x => {
      this.submitted = false;
      this.errors = null;
      this.reset();
      this.success = "Pomyslnie zmieniono dane apteki !";


    }, error1 => {
      this.success = null;
      this.submitted = true;
      this.errors = error1.error;
    });
    this.submittSearch = false;
  }

  reset() {

    this.initDetails();
  }

}

