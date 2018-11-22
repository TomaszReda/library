import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient, HttpParams} from "@angular/common/http";

declare var ol: any;

@Component({
  selector: 'app-add-library',
  templateUrl: './add-library.component.html',
  styleUrls: ['./add-library.component.css']
})
export class AddLibraryComponent implements OnInit {

  public map: any;
  public mapLat = 52.2051;
  public mapLng = 21.0158;
  public mapDefaultZoom = 10;

  url = 'https://nominatim.openstreetmap.org/search';

  public formAddLibrary: FormGroup;

  constructor(private http: HttpClient) {

  }


  ngOnInit(): void {
    this.initialize_map();
    this.initialize_form();
  }


  searchOnMap() {
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

        this.add_map_point(x[0].lat, x[0].lon)
      }
    );
  }

  initialize_form() {
    this.formAddLibrary = new FormGroup({
      name: new FormControl(null),
      street: new FormControl(null),
      number: new FormControl(null),
      local: new FormControl(null),
      postalCode: new FormControl(null),
      city: new FormControl(null),
      email: new FormControl(null),


    })
  }


  initialize_map2(mapLng, mapLat) {
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
        center: ol.proj.fromLonLat([mapLng, mapLat]),
        zoom: this.mapDefaultZoom
      })
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
        center: ol.proj.fromLonLat([this.mapLng, this.mapLat]),
        zoom: this.mapDefaultZoom
      })
    });
  }

  add_map_point(lat, lng) {
    console.log("cos");
    var vectorLayer = new ol.layer.Vector({
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
    this.map.addLayer(vectorLayer);
  }


}
