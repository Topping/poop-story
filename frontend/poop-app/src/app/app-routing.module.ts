import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { FrontPageComponent } from "./front-page/front-page.component";
import { PoopMapComponent } from "./poop-map/poop-map.component";
import { UserProfileComponent } from "./user-profile/user-profile.component";

import { AuthGuard } from "@auth0/auth0-angular";

const routes: Routes = [
  { path: "", component: FrontPageComponent },
  { path: "poop-map", canActivate: [AuthGuard], component: PoopMapComponent },
  { path: "user", canActivate: [AuthGuard], component: UserProfileComponent },
  { path: "**", component: FrontPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
