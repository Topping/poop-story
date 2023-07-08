import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "./../environments/environment";

@Injectable({
  providedIn: "root",
})
export class VisitService {
  constructor(private http: HttpClient) {}

  private visitsUrl = encodeURI(environment.apiOriginUrl + "/api/private/visit");

  // TODO: optional country query
  getVisits() {
    return this.http.get(this.visitsUrl);
  }

  postVisit(visitData: any) {
    return this.http.post(this.visitsUrl, visitData);
  }
}
