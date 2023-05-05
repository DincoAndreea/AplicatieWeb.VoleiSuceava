import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Utilizator } from 'src/app/model/utilizator';

@Injectable({ providedIn: `root` })
export class AutentificareService {
  constructor(private _http: HttpClient) {}
  autentificare(utilizator: Utilizator): Observable<any> {
    let utilziatorLocal = this.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.post(
      'http://localhost:4200/api/utilizatori/logare',
      utilizator,
      {
        headers: headers,
        responseType: `json`,
      }
    );
  }

  delogare() {
    let utilziatorLocal = btoa(`vizitator:parola`);
    localStorage.setItem(`localUser`, utilziatorLocal);
  }

  delogare2(utilizator: Utilizator): Observable<any> {
    this.delogare();
    let utilizatorLocal = this.getUtilizatorActiv();
        
    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilizatorLocal,
    });

    return this._http.post(
      'http://localhost:4200/api/utilizatori/logare',
      utilizator,
      {
        headers: headers,
        responseType: `json`,
      }
    );
  }
  getUtilizatorActiv() {
    let utilziatorLocal = localStorage.getItem(`localUser`);
    while (utilziatorLocal == undefined) {
      this.delogare();
      utilziatorLocal = localStorage.getItem(`localUser`);
    }
    return utilziatorLocal;
  }
}
