import { Component } from "@angular/core";
import { AuthService } from "@auth0/auth0-angular";

@Component({
  selector: "app-user-profile",
  templateUrl: "./user-profile.component.html",
  styleUrls: ["./user-profile.component.scss"],
})
export class UserProfileComponent {
  numPoops: number;

  constructor(public auth: AuthService) {
    this.numPoops = Math.floor(Math.random() * 100);
  }
}
