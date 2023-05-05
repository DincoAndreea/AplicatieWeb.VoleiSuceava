import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { ISperante } from 'src/app/model/sperante';
import { IUpdateSperante } from 'src/app/model/update-sperante';
import { Utilizator } from 'src/app/model/utilizator';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';

@Injectable({ providedIn: `root`})
export class SperanteService {
    constructor(
        private _http: HttpClient,
        private autentificare: AutentificareService
    ){}
    getSperante(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get('http://localhost:4200/api/voleijuvenil/sperante', {
            headers: headers,
        });
    }

    addSperante(sperante:ISperante){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.post('http://localhost:4200/api/voleijuvenil/sperante/administrare', sperante, {
            headers: headers,
            responseType: `json`,
        });
    }

    updateSperante(sperante:IUpdateSperante){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.put(`http://localhost:4200/api/voleijuvenil/sperante/administrare/${sperante.id}`, sperante, {
            headers: headers,
            responseType: `json`,
        });
    }

    deleteSperante(sperante:IUpdateSperante){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.delete(`http://localhost:4200/api/voleijuvenil/sperante/administrare/${sperante.id}`, {
            headers: headers,
            responseType: `json`,
        });
    }
}