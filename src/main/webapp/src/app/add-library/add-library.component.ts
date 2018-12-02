import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpParams} from "@angular/common/http";
import {LibraryService} from "../service/library.service";
import {AuthService} from "../service/auth.service";


declare var ol: any;

@Component({
  selector: 'app-add-library',
  templateUrl: './add-library.component.html',
  styleUrls: ['./add-library.component.css']
})
export class AddLibraryComponent implements OnInit {

  public map: any;
  public mapLat: string = '52.2051';
  public mapLng: string = '21.0158';
  public mapDefaultZoom = 10;
  public vectorLayer;
  success = null;

  errors = null;

  errors2 = null;


  submitted = false;

  url = 'https://nominatim.openstreetmap.org/search';

  public formAddLibrary: FormGroup;

  constructor(private http: HttpClient, private libraryService: LibraryService, private auth: AuthService) {

  }


  ngOnInit(): void {
    this.submitted = false;
    this.initialize_map();
    this.initialize_form();
  }


  addLibrary() {
    this.submitted = true;
    this.errors2 = null;
    this.success = null;


    if (!this.formAddLibrary.valid) {
      return "blad";
    }

    const object = this.formAddLibrary.getRawValue();

    object.latitude = this.mapLat;
    object.longitude = this.mapLng;
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
    this.mapLat = '52.2051';
    this.mapLng = '21.0158';
    this.setCenter();
    if (this.vectorLayer) {
      this.map.removeLayer(this.vectorLayer);
    }
  }

  searchOnMap() {
    this.success = null;

    this.errors = null;
    let adress = this.formAddLibrary.value.city;
    if (this.formAddLibrary.value.street) {
      adress += '+' + this.formAddLibrary.value.street;
    }
    if (this.formAddLibrary.value.number) {
      adress += '+' + this.formAddLibrary.value.number;
    }


    let params = new HttpParams().set('q', adress).append('format', 'json');
    this.http.get(this.url, {params: params}).subscribe(
      x => {

        if (x[0]) {
          let latitude = x[0].lat;
          let longitude = x[0].lon;

          this.mapLat = latitude;
          this.mapLng = longitude;

          this.setCenter();
          this.add_map_point(latitude, longitude);
          this.errors2 = null;

        } else {
          this.errors2 = "Nie ma takiej lokalizacji!";
          this.mapLat = '52.2051';
          this.mapLng = '21.0158';
          this.setCenter();
          if (this.vectorLayer) {
            this.map.removeLayer(this.vectorLayer);
          }
        }

      }
    );

  }

  setCenter() {
    var view = this.map.getView();
    view.setCenter(ol.proj.fromLonLat([parseFloat(this.mapLng), parseFloat(this.mapLat)]));
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


  initialize_map() {
    this.map = new ol.Map({
      target: "map",
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM({
            url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png"
          })
        })
      ],
      view: new ol.View({
        center: ol.proj.fromLonLat([parseFloat(this.mapLng), parseFloat(this.mapLat)]),
        zoom: this.mapDefaultZoom
      })
    });
  }

  add_map_point(lat, lng) {
    if (this.vectorLayer) {
      this.map.removeLayer(this.vectorLayer);
    }
    this.vectorLayer = new ol.layer.Vector({
      source: new ol.source.Vector({
        features: [new ol.Feature({
          geometry: new ol.geom.Point(ol.proj.transform([parseFloat(lng), parseFloat(lat)], 'EPSG:4326', 'EPSG:3857')),
        })]
      }),
      style: new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 0.5],
          anchorXUnits: "fraction",
          anchorYUnits: "fraction",
          src: "https://upload.wikimedia.org/wikipedia/commons/e/ec/RedDot.svg"
        })
      })
    });
    this.map.addLayer(this.vectorLayer);

  }

}
