import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';
import { IPaginare } from 'src/app/model/Paginare';
import { INoutateAdminFiltru } from 'src/app/model/noutate-admin-filtru';
import { INoutateDetaliat } from 'src/app/model/noutate-detaliat';
import { INoutatePostPut } from 'src/app/model/noutate-post-put';

@Injectable({ providedIn: `root` })
export class NoutatiServices {
    constructor(private _http: HttpClient, private autentificare: AutentificareService) { }
    private numarElemente: number = 6;


    getNoutati(paginare: IPaginare): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post('http://localhost:4200/api/stiri/paginare', paginare, {
            headers: headers,
            responseType: `json`,
        });
    };

    getNoutatiPostate(paginare: IPaginare): Observable<any>{
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

    getNoutateDetaliat(idStire:number){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/stiri/${idStire}`, {
            headers: headers,
        }).pipe(
            map<any, any[]>(
                (response) => { return response }
            ),
            catchError(error => {
                return throwError('Eroare la preluarea paginii.');
            })
        );
    }
    
    getPagini(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/stiri/paginare/${this.numarElemente}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la paginare.');
                })
            );
    }

    getPaginiPostate(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/stiri/postate/paginare/${this.numarElemente}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la paginare.');
                })
            );
    }

    getPaginiProgramate(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/stiri/programate/paginare/${this.numarElemente}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la paginare.');
                })
            );
    }

    getNoutatiFiltrate(noutateFiltru:INoutateAdminFiltru ,tipStire:number, tipMedia:string, tipStireCronologic:number ):Observable<any>{
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/stiri/filtru/${tipStire}/${tipMedia}/${tipStireCronologic}`, noutateFiltru, {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
                (response) => { return response }
            ),
            catchError(error => {
                return throwError('Eroare la NoutatiService: getNoutatiFiltrate.');
            })
        );
    }

    getNoutatiFiltratePostate(noutateFiltru:INoutateAdminFiltru ,tipStire:number, tipMedia:string, tipStireCronologic:number ):Observable<any>{
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/stiri/postate/filtru/${tipStire}/${tipMedia}/${tipStireCronologic}`, noutateFiltru, {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
                (response) => { return response }
            ),
            catchError(error => {
                return throwError('Eroare la NoutatiService: getNoutatiFiltrate.');
            })
        );
    }

    getNoutatiFiltrateProgramate(noutateFiltru:INoutateAdminFiltru ,tipStire:number, tipMedia:string, tipStireCronologic:number ):Observable<any>{
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/stiri/programate/filtru/${tipStire}/${tipMedia}/${tipStireCronologic}`, noutateFiltru, {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
                (response) => { return response }
            ),
            catchError(error => {
                return throwError('Eroare la NoutatiService: getNoutatiFiltrate.');
            })
        );
    }

    postNoutateNoua(noutateNoua:INoutatePostPut):Observable<any>
    {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });
        //console.log("Suntem pe noutate noua in SERVICE");
        
        return this._http.post('http://localhost:4200/api/stiri/administrare', noutateNoua, {
            headers: headers,
            responseType: `json`,
        })
        .pipe(
            map<any, any[]>(
                (response) => { 
                    //console.log("Pipe reusit");
                    return response }
            ),
            catchError(error => {
                return throwError('Eroare la NoutatiService: postNoutateNoua.');
            })
        );
    }
    putNoutateActualizata(noutateNoua:INoutatePostPut):Observable<any>
    {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });
        
        return this._http.put(`http://localhost:4200/api/stiri/administrare/${noutateNoua.id}`, noutateNoua, {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
                (response) => { return response }
            ),
            catchError(error => {
                return throwError('Eroare la NoutatiService: putNoutateActualizata.');
            })
        );
    }
    deleteNoutate(id: number): Observable<unknown>
    {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.delete(`http://localhost:4200/api/stiri/administrare/${id}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la stergerea stirei.');
                })
            );
    }
}