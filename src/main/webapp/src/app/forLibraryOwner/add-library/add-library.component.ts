import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpParams} from "@angular/common/http";
import {LibraryService} from "../../service/library.service";
import {AuthService} from "../../service/auth.service";
import {MapServiceService} from "../../service/map-service.service";


declare var ol: any;

@Component({
  selector: 'app-add-library',
  templateUrl: './add-library.component.html',
  styleUrls: ['./add-library.component.css']
})
export class AddLibraryComponent implements OnInit {


  success = null;

  errors = null;

  errors2 = null;


  submitted = false;

  submittSearch = true;

  url = 'https://nominatim.openstreetmap.org/search';

  public formAddLibrary: FormGroup;

  constructor(private http: HttpClient, private libraryService: LibraryService, private auth: AuthService, private mapService: MapServiceService) {

  }

  keyUp() {
    this.submittSearch = true;
  }


  ngOnInit(): void {

    this.submittSearch=false;
    this.submitted = false;
    this.mapService.mapLng = '21.0158';
    this.mapService.mapLat = '52.2051';
    this.mapService.initialize_map();
    this.initialize_form();
  }


  addLibrary() {
    this.submitted = true;
    this.errors2 = null;
    this.success = null;


    if (this.submittSearch === true ) {
      this.errors = "Zmieniłes cos w adresie. Musisz zaznaczyć na mapie lokalizacje";
      return;
    }

    if (!this.formAddLibrary.valid) {
      return "blad";
    }

    const object = this.formAddLibrary.getRawValue();

    object.latitude = this.mapService.mapLat;
    object.longitude = this.mapService.mapLng;
    object.userID = this.auth.user.id;

    this.libraryService.addLibrary(object).subscribe(x => {
      this.submitted = false;
      this.errors = null;
      this.reset();
      this.success = "Pomyslnie zmieniono dane biblioteki!";


    }, error1 => {
      this.success = null;
      this.submitted = true;
      this.errors = error1.error;
    })

  }

  reset() {
    this.success = null;
    this.errors = null;
    this.errors2 = null;
    this.formAddLibrary.reset();
    this.mapService.mapLng = '21.0158';
    this.mapService.mapLat = '52.2051';
    this.mapService.setCenter();
    this.mapService.resetPoint();

  }

  searchOnMap() {
    this.success = null;

    this.errors = null;
    let adress = "";
    if (this.formAddLibrary.value.street) {
      adress +=  this.formAddLibrary.value.street;
    }
    if (this.formAddLibrary.value.number) {
      if(adress==null)
        adress +=   this.formAddLibrary.value.number;
      else
        adress += '+' + this.formAddLibrary.value.number;

    }
    adress += "+" + this.formAddLibrary.value.city;
    adress += '+' + this.formAddLibrary.value.postalCode;


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
          this.errors2 = null;

        } else {
          this.errors2 = "Nie ma takiej lokalizacji! Upewnij sie czy podajesz poprawny adres (Obowiązkowo Miasto,Kod pocztowy,numer)!";
          this.mapService.mapLng = '21.0158';
          this.mapService.mapLat = '52.2051';
          this.mapService.setCenter();

        }

      }
    );

  }


  initialize_form() {
    this.formAddLibrary = new FormGroup({
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
  }


}
