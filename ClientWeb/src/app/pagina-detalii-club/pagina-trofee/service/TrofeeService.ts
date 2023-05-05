import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { Utilizator } from 'src/app/model/utilizator';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';

@Injectable({ providedIn: `root` })
export class TrofeeService {
  constructor(
    private _http: HttpClient,
    private autentificare: AutentificareService
  ) {}
  getTrofee(): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.get('http://localhost:4200/api/personal/echipe/trofee', {
      headers: headers,
      responseType: `json`,
    });
  }

  getTrofeePag(): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.get('http://localhost:4200/api/club/trofee', {
      headers: headers,
      responseType: `json`,
    });
  }
}
