import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { FrontPageComponent } from "./front-page/front-page.component";
import { PoopMapComponent } from "./poop-map/poop-map.component";

const routes: Routes = [
  { path: "", component: FrontPageComponent },
  { path: "poop-map", component: PoopMapComponent },
  { path: "**", component: FrontPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
