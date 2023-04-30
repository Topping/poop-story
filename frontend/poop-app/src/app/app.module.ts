import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";

import { AuthModule } from "@auth0/auth0-angular";
import { LoginButtonComponent } from './login-button/login-button.component';

@NgModule({
  declarations: [AppComponent, LoginButtonComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule.forRoot({
      domain: "poopstory.eu.auth0.com",
      clientId: "4UDKesdZeGk4oZpbNZ6mA5jxi8wcjNwg",
      authorizationParams: {
        redirect_uri: window.location.origin,
      },
    }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
