import { Component, AfterViewInit } from "@angular/core";
import * as L from "leaflet";

@Component({
  selector: "app-poop-map",
  templateUrl: "./poop-map.component.html",
  styleUrls: ["./poop-map.component.scss"],
})
export class PoopMapComponent implements AfterViewInit {
  map: L.Map | undefined;

  private currentPosition = navigator.geolocation.getCurrentPosition(({ coords }) => {
    console.log({ coords });
    this.initMap(coords);
  });

  private initMap(position: GeolocationCoordinates): void {
    this.map = L.map("map", {
      // center: [39.8282, -98.5795],
      center: [position.latitude, position.longitude],
      zoom: 15,
    });

    const tiles = L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    });

    tiles.addTo(this.map);
  }

  constructor() {}

  ngAfterViewInit(): void {
    // this.initMap();
    // this.currentPosition;
  }
}
