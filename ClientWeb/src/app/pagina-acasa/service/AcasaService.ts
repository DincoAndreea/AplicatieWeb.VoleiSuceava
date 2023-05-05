import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { IPaginare } from 'src/app/model/Paginare';
import { EMPTY } from 'rxjs';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';

@Injectable({ providedIn: `root` })
export class AcasaService {
    constructor(private _http: HttpClient, private autentificare: AutentificareService) { }
    
    getNoutati(paginare:IPaginare): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post('http://localhost:4200/api/stiri/postate/paginare', paginare, {
            headers: headers,
            responseType: `json`,
        });
    };

    getSeniori(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.get('http://localhost:4200/api/personal/seniori', {
            headers: headers,
            responseType: `json`,
        });
    };

    getMeciAnterior(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.get('http://localhost:4200/api/meciuri/anterior', {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
              (response) => { return response }
            ),
            catchError(error => {
              return throwError(' ');
            })
          );
    };

    getMeciViitor(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.get('http://localhost:4200/api/meciuri/viitor', {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
              (response) => { return response }
            ),
            catchError(error => {
              return throwError('Eroare la preluarea meciului viitor.');
            })
          );
    };


}