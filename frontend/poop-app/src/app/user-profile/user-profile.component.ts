import { Component } from "@angular/core";
import { AuthService } from "@auth0/auth0-angular";
import { VisitService } from "../visit.service";

@Component({
  selector: "app-user-profile",
  templateUrl: "./user-profile.component.html",
  styleUrls: ["./user-profile.component.scss"],
})
export class UserProfileComponent {
  numPoops: number;

  constructor(public auth: AuthService, private visitService: VisitService) {
    this.numPoops = 0;
    this.getNumPoops();
  }

  getNumPoops() {
    // TODO: typings, loaders
    this.visitService.getVisits().subscribe((res) => {
      const amountOfVisits = (res as unknown as any).length;
      this.numPoops = amountOfVisits;
    });
  }
}
