import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { IJuniori } from 'src/app/model/juniori';
import { IUpdateJuniori } from 'src/app/model/update-juniori';
import { Utilizator } from 'src/app/model/utilizator';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';

@Injectable({ providedIn: `root`})
export class JunioriService {
    constructor(
        private _http: HttpClient,
        private autentificare: AutentificareService
    ){}
    getJuniori(): Observable<any> {
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.get('http://localhost:4200/api/voleijuvenil/juniori', {
            headers: headers,
        });
    }

    addJuniori(juniori:IJuniori){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.post('http://localhost:4200/api/voleijuvenil/juniori/administrare', juniori, {
            headers: headers,
            responseType: `json`,
        });
    }

    updateJuniori(juniori:IUpdateJuniori){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.put(`http://localhost:4200/api/voleijuvenil/juniori/administrare/${juniori.id}`, juniori, {
            headers: headers,
            responseType: `json`,
        });
    }

    deleteJuniori(juniori:IUpdateJuniori){
        let utilizatorLocal = this.autentificare.getUtilizatorActiv();

        const headers = new HttpHeaders({
            authorization: 'Basic ' + utilizatorLocal,
        });

        return this._http.delete(`http://localhost:4200/api/voleijuvenil/juniori/administrare/${juniori.id}`, {
            headers: headers,
            responseType: `json`,
        });
    }
}