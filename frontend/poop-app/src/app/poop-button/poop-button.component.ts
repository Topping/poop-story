import { Component } from "@angular/core";
import { VisitService } from "../visit.service";
import { emojisplosion } from "emojisplosion";

var cumulativeOffset = function (element: any) {
  var top = 0,
    left = 0;
  do {
    top += element.offsetTop || 0;
    left += element.offsetLeft || 0;
    element = element.offsetParent;
  } while (element);

  return {
    top: top,
    left: left,
  };
};

@Component({
  selector: "app-poop-button",
  templateUrl: "./poop-button.component.html",
  styleUrls: ["./poop-button.component.scss"],
})
export class PoopButtonComponent {
  constructor(private visitService: VisitService) {}

  hasPooped = false;
  poopErrored = false;

  public doThePoop(): void {
    this.logVisit();
  }

  // TODO: a button really shouldn't know all this 🤣
  private logVisit() {
    navigator.geolocation.getCurrentPosition(({ coords }) => {
      // TODO: type selection, rating selection, accuracy adjustment
      const visitData = {
        id: crypto.randomUUID(),
        type: "POO",
        when: new Date().toISOString(),
        location: {
          longitude: coords.longitude,
          latitude: coords.latitude,
        },
        rating: 0,
      };
      this.visitService.postVisit(visitData).subscribe({
        next: () => {
          this.explode();
          this.hasPooped = true;
          console.log("YOU POOPED 💩");
        },
        error: (err) => {
          console.error("Something went wrong. Here's the error message:", err);
          this.poopErrored = true;
        },
      });
    });
  }

  private explode() {
    emojisplosion({
      emojis: ["💩"],
      position() {
        // https://stackoverflow.com/questions/1480133
        const element = document.querySelector("#poop-button");
        const offset = cumulativeOffset(element);

        return {
          x: offset.left + element!.clientWidth / 2,
          y: offset.top + element!.clientHeight / 2,
        };
      },
    });
  }
}
