import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { ICadeti } from 'src/app/model/cadeti';
import { IUpdateCadeti } from 'src/app/model/update-cadeti';
import { Utilizator } from 'src/app/model/utilizator';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';

@Injectable({ providedIn: `root`})
export class CadetiService {
    constructor(
        private _http: HttpClient,
        private autentificare: AutentificareService
    ){}
    getCadeti(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get('http://localhost:4200/api/voleijuvenil/cadeti', {
            headers: headers,
        });
    }

    addCadeti(cadeti:ICadeti){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        //console.log("adauga");

        return this._http.post('http://localhost:4200/api/voleijuvenil/cadeti/administrare', cadeti, {
            headers: headers,
            responseType: `json`,
        });
    }

    updateCadeti(cadeti:ICadeti){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        //console.log("update");

        return this._http.put(`http://localhost:4200/api/voleijuvenil/cadeti/administrare/${cadeti.id}`, cadeti, {
            headers: headers,
            responseType: `json`,
        });
    }

    deleteCadeti(cadeti:ICadeti){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        //console.log("delete");

        return this._http.delete(`http://localhost:4200/api/voleijuvenil/cadeti/administrare/${cadeti.id}`, {
            headers: headers,
        });
    }
}