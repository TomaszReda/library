import {Injectable, OnInit} from '@angular/core';
declare var ol: any;

@Injectable({
  providedIn: 'root'
})
export class MapServiceService  implements OnInit{

  public mapDefaultZoom = 10;
  public map: any;
  public mapLat: string = '52.2051';
  public mapLng: string = '21.0158';
  public vectorLayer;

  constructor() {

  }

  ngOnInit(): void {

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

  setCenter() {
    var view = this.map.getView();
    view.setCenter(ol.proj.fromLonLat([parseFloat(this.mapLng), parseFloat(this.mapLat)]));
  }



  add_map_point(lat, lng) {
    this.setCenter();
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
