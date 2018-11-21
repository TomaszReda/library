import {Component, OnInit} from '@angular/core';

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

  constructor() {

  }


  ngOnInit(): void {
    this.initialize_map();
    this.add_map_point(this.mapLat,this.mapLng);
  }



cos(){
    console.log("cos");
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
