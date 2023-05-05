import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Subject, Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
import { IAntrenor } from "src/app/model/antrenor";
import { IEchipa } from "src/app/model/echipa";
import { IJucator } from "src/app/model/jucator";
import { IPaginare } from "src/app/model/Paginare";
import { AutentificareService } from "src/app/pagina-autentificare/service/AutentificareService";

@Injectable({ providedIn: `root`})
export class PersonalService {
    constructor(
        private _http: HttpClient,
        private autentificare: AutentificareService
    ){}
    private numarElemente: number = 8;
    
    getJucatori(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/personal/jucatori/paginare/${this.numarElemente}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluare jucatori cu paginare.');
                })
            );
    }

    getAllJucatori(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/personal/jucatori`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluare jucatori fara paginare.');
                })
            );
    }

    getJucatoriPag(paginare: IPaginare): Observable<any>{
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post('http://localhost:4200/api/personal/jucatori/paginare', paginare, {
            headers: headers,
            responseType: `json`,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluare jucatori post paginare.');
                })
        );
    }

    getAntrenori(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/personal/antrenori/paginare/${this.numarElemente}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluare antrenori cu paginare.');
                })
            );
    }

    getAllAntrenori(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/personal/antrenori`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluare antrenori fara paginare.');
                })
            );
    }

    getAntrenoriPag(paginare: IPaginare): Observable<any>{
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post('http://localhost:4200/api/personal/antrenori/paginare', paginare, {
            headers: headers,
            responseType: `json`,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluare antrenori post paginare.');
                })
        );
    }

    getAntrenoriFiltru(valoare: string, paginare: IPaginare):Observable<any>{
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/personal/antrenori/filtrare/${valoare}`, paginare, {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
                (response) => { return response }
            ),
            catchError(error => {
                return throwError('Eroare la filtrare antrenori.');
            })
        );
    }

    getJucatoriFiltru(valoare: string, paginare: IPaginare):Observable<any>{
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization:
                'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/personal/jucatori/filtrare/${valoare}`, paginare, {
            headers: headers,
            responseType: `json`,
        }).pipe(
            map<any, any[]>(
                (response) => { return response }
            ),
            catchError(error => {
                return throwError('Eroare la filtrare jucatori.');
            })
        );
    }

    addJucator(juc: IJucator): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/personal/jucatori/administrare`, juc, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la adaugare jucator.');
                })
            );
    }

    addAntrenor(ant: IAntrenor): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/personal/antrenori/administrare`, ant, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la adaugare antrenor.');
                })
            );
    }

    updateJucator(juc: IJucator): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.put(`http://localhost:4200/api/personal/jucatori/administrare/${juc.id}`, juc, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la actualizare jucator.');
                })
            );
    }

    updateAntrenor(ant: IAntrenor): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.put(`http://localhost:4200/api/personal/antrenori/administrare/${ant.id}`, ant, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la actualizare antrenor.');
                })
            );
    }

    deleteJucator(juc: IJucator): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.delete(`http://localhost:4200/api/personal/jucatori/administrare/${juc.id}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la stergere jucator.');
                })
            );
    }

    deleteAntrenor(ant: IAntrenor): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.delete(`http://localhost:4200/api/personal/antrenori/administrare/${ant.id}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la stergere antrenor.');
                })
            );
    }

    getEchipe(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/personal/echipe/jucatori`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluat echipe.');
                })
            );
    }

    getEchipeJucatori(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get(`http://localhost:4200/api/personal/echipe/antrenori`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la preluat echipe.');
                })
            );
    }

    addEchipa(ech: IEchipa): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.post(`http://localhost:4200/api/personal/echipe/administrare`, ech, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la adaugare echipa.');
                })
            );
    }

    updateEchipa(ech: IEchipa): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.put(`http://localhost:4200/api/personal/echipe/administrare/${ech.id}`, ech, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la actualizare echipa.');
                })
            );
    }

    deleteEchipa(ech: IEchipa): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.delete(`http://localhost:4200/api/personal/echipe/administrare/${ech.id}`, {
            headers: headers,
        })
            .pipe(
                map<any, any[]>(
                    (response) => { return response }
                ),
                catchError(error => {
                    return throwError('Eroare la stergere echipa.');
                })
            );
    }

}