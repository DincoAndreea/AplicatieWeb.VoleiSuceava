import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { IMinivolei } from 'src/app/model/minivolei';
import { IUpdateMinivolei } from 'src/app/model/update-minivolei';
import { Utilizator } from 'src/app/model/utilizator';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';

@Injectable({ providedIn: `root`})
export class MinivoleiService {
    constructor(
        private _http: HttpClient,
        private autentificare: AutentificareService
    ){}
    getMinivolei(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get('http://localhost:4200/api/voleijuvenil/minivolei', {
            headers: headers,
        });
    }

    addMinivolei(minivolei:IMinivolei){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.post('http://localhost:4200/api/voleijuvenil/minivolei/administrare', minivolei, {
            headers: headers,
            responseType: `json`,
        });
    }

    updateMinivolei(minivolei:IUpdateMinivolei){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.put(`http://localhost:4200/api/voleijuvenil/minivolei/administrare/${minivolei.id}`, minivolei, {
            headers: headers,
            responseType: `json`,
        });
    }

    deleteMinivolei(minivolei:IUpdateMinivolei){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.delete(`http://localhost:4200/api/voleijuvenil/minivolei/administrare/${minivolei.id}`, {
            headers: headers,
            responseType: `json`,
        });
    }
}