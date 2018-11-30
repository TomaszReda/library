import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpParams} from "@angular/common/http";
import {LibraryService} from "../../service/library.service";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute} from "@angular/router";
import {Library} from "../../model/library/library.model";

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

  errors2 = null;


  submitted = false;

  url = 'https://nominatim.openstreetmap.org/search';

  public forModifyLibrary: FormGroup;

  constructor(private http: HttpClient, private libraryService: LibraryService, private auth: AuthService, private router: ActivatedRoute) {

  }


  ngOnInit(): void {
    this.initDetails();
    this.initialize_map(this.mapLng, this.mapLat);

  }


  initDetails() {
    this.submitted = false;
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
      this.add_map_point(x.latitude,x.longitude);
      }
    )

  }


  searchOnMap() {
    this.success = null;

    this.errors = null;
    let adress = this.forModifyLibrary.value.city;
    if (this.forModifyLibrary.value.street) {
      adress += '+' + this.forModifyLibrary.value.street;
    }
    if (this.forModifyLibrary.value.number) {
      adress += '+' + this.forModifyLibrary.value.number;
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

  initialize_map(mapLng, mapLat) {
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
        center: ol.proj.fromLonLat([parseFloat(mapLng), parseFloat(mapLat)]),
        zoom: this.mapDefaultZoom
      })
    });
  }

  setCenter() {
    var view = this.map.getView();
    view.setCenter(ol.proj.fromLonLat([parseFloat(this.mapLng), parseFloat(this.mapLat)]));
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


  addLibrary() {
  }

  reset() {

    this.initDetails();
  }

}

