import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";

import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";

import { AuthModule, AuthHttpInterceptor } from "@auth0/auth0-angular";
import { LoginButtonComponent } from "./login-button/login-button.component";
import { PoopMapComponent } from "./poop-map/poop-map.component";
import { FrontPageComponent } from "./front-page/front-page.component";
import { UserProfileComponent } from "./user-profile/user-profile.component";
import { PoopButtonComponent } from "./poop-button/poop-button.component";
import { environment } from "./../environments/environment";

@NgModule({
  declarations: [
    AppComponent,
    LoginButtonComponent,
    PoopMapComponent,
    FrontPageComponent,
    UserProfileComponent,
    PoopButtonComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    AuthModule.forRoot({
      domain: environment.auth0.domain,
      clientId: environment.auth0.clientId,
      authorizationParams: {
        redirect_uri: window.location.origin,
        audience: "https://api.poopstory.io",
      },
      httpInterceptor: {
        allowedList: ["http://localhost:8080/api/private/visit"],
      },
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHttpInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
