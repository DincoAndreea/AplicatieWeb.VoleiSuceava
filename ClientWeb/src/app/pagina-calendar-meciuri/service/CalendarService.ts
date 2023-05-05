import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, Subject, throwError } from 'rxjs';
import { ICampionat } from 'src/app/model/campionat';
import { ICampionatDTO } from 'src/app/model/campionatDTO';
import { IDivizii } from 'src/app/model/divizii';
import { ILocalitate } from 'src/app/model/localitate';
import { ILocalitati } from 'src/app/model/localitate-adaugare';
import { IMeciA } from 'src/app/model/meci-adaugare';
import { IMeciC } from 'src/app/model/meci-complet';
import { IMeciFiltruCuData } from 'src/app/model/meci-filtruCuData';
import { IMeciFiltruCuDataPaginat } from 'src/app/model/meci-filtruCuDataPaginat';
import { IMeciFiltruFaraData } from 'src/app/model/meci-filtruFaraData';
import { IMeciFiltruFaraDataPaginat } from 'src/app/model/meci-filtruFaraDataPaginat';
import { IPaginare } from 'src/app/model/Paginare';
import { Utilizator } from 'src/app/model/utilizator';
import { AutentificareService } from 'src/app/pagina-autentificare/service/AutentificareService';

@Injectable({ providedIn: `root` })
export class CalendarService {
  
  constructor(
    private _http: HttpClient,
    private autentificare: AutentificareService
  ) { }

  getMeciViitor(): Observable<any> {
    let utilizatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization:
        'Basic ' + utilizatorLocal,
    });

    return this._http.get(`http://localhost:4200/api/meciuri/viitor/`, {
      headers: headers,
    })
      .pipe(
        map<any, any[]>(
          (response) => { return response }
        ),
        catchError(error => {
          return throwError('Eroare la preluare meciuri viitor.');
        })
      );
  };

  getMeciAnterior(): Observable<any> {
    let utilizatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization:
        'Basic ' + utilizatorLocal,
    });

    return this._http.get(`http://localhost:4200/api/meciuri/anterior/`, {
      headers: headers,
    })
      .pipe(
        map<any, any[]>(
          (response) => { return response }
        ),
        catchError(error => {
          return throwError('Eroare la preluare meciuri viitor.');
        })
      );
  };

  getDivizii(): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.get('http://localhost:4200/api/meciuri/divizii', {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  addDivizii(divizie: string): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.post('http://localhost:4200/api/meciuri/divizii/administrare', divizie, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  updateDivizii(divizie: IDivizii,id: number): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.put(`http://localhost:4200/api/meciuri/divizii/administrare/${id}`, divizie.nume, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  deleteDivizii(divizie: IDivizii): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.delete(`http://localhost:4200/api/meciuri/divizii/administrare/${divizie.id}`, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  getCampionate(): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.get('http://localhost:4200/api/meciuri/campionate', {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  addCampionate(campionat: ICampionatDTO): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.post('http://localhost:4200/api/meciuri/campionate/administrare', campionat, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  updateCampionate(campionat: ICampionatDTO, id: number): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.put(`http://localhost:4200/api/meciuri/campionate/administrare/${id}`, campionat, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  deleteCampionate(campionat: ICampionat): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.delete(`http://localhost:4200/api/meciuri/campionate/administrare/${campionat.id}`, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  getLocatii(): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.get('http://localhost:4200/api/meciuri/localitati', {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  addLocatii(localitate: ILocalitati): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.post('http://localhost:4200/api/meciuri/localitati/administrare', localitate, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  updateLocatii(localitate: ILocalitati, id: number): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.put(`http://localhost:4200/api/meciuri/localitati/administrare/${id}`, localitate, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  deleteLocatii(localitate: ILocalitate): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.delete(`http://localhost:4200/api/meciuri/localitati/administrare/${localitate.id}`, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  getMeciuri(paginare: IPaginare): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.post('http://localhost:4200/api/meciuri', paginare, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  getMeci(idMeci: number): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.get(`http://localhost:4200/api/meciuri/${idMeci}`, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare meci dupa id.');
      })
    );
  };

  addMeci(meci: IMeciA): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.post('http://localhost:4200/api/meciuri/administrare', meci, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  updateMeci(meci: IMeciA): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.put(`http://localhost:4200/api/meciuri/administrare/${meci.id}`, meci, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  deleteMeci(meci: IMeciC): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.delete(`http://localhost:4200/api/meciuri/administrare/${meci.id}`, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluare divizii.');
      })
    );
  };

  storeFiltru(meciuriCuData: IMeciFiltruCuDataPaginat = 
    { 'idDivizie': -10, 'tipMeci': 'x', 'dataIncepere': new Date(), 'dataSfarsit': new Date(), 'paginareDto':{
    'numarElemente':6,
    'numarPagina':0
  } }, 
  meciuriFaraData: IMeciFiltruFaraDataPaginat = { 'idDivizie': -10, 'tipMeci': 'x', 'paginareDto':{
    'numarElemente':6,
    'numarPagina':0
  } }) {
    if (meciuriCuData.idDivizie != -10) {
      localStorage.setItem("meciCuDataFiltruPaginat",JSON.stringify(meciuriCuData));
    }
    else {
      localStorage.setItem("meciFaraDataFiltruPaginat",JSON.stringify(meciuriFaraData));
    }
  }
  retrieveFiltruCuDataPaginat(): IMeciFiltruCuDataPaginat {
    return JSON.parse(localStorage.getItem("meciCuDataFiltruPaginat")||"");
  }
  retrieveFiltruFaraDataPaginat(): IMeciFiltruFaraDataPaginat {
    return JSON.parse(localStorage.getItem("meciFaraDataFiltruPaginat")||"");
  }
  resetFiltruCuDataPaginat() {
    localStorage.setItem("meciCuDataFiltruPaginat",JSON.stringify({
      'idDivizie': -10,
      'tipMeci': 'x',
      'dataIncepere': new Date(),
      'dataSfarsit': new Date(),
      'paginareDto':{
        'numarElemente':6,
        'numarPagina':0
      }
    }));
  }
  resetFiltruFaraDataPaginat() {
    localStorage.setItem("meciFaraDataFiltruPaginat", JSON.stringify({
      'idDivizie': -10,
      'tipMeci': 'x',
      'paginareDto':{
        'numarElemente':6,
        'numarPagina':0
      }
    }));
  }

  getMeciuriFiltratePaginate(meciuriCuData: IMeciFiltruCuDataPaginat = { 'idDivizie': -10, 'tipMeci': 'x', 'dataIncepere': new Date(), 'dataSfarsit': new Date(), 'paginareDto':{'numarElemente':6,'numarPagina':0} }, meciuriFaraData: IMeciFiltruFaraDataPaginat = { 'idDivizie': -10, 'tipMeci': 'x' , 'paginareDto':{'numarElemente':6,'numarPagina':0}}): Observable<any> {
    let utilizatorLocal = this.autentificare.getUtilizatorActiv();
    let aux;
    if (meciuriCuData.idDivizie != -10) {
      aux = meciuriCuData;
      // console.log("cu data");
    }
    else {
      aux = meciuriFaraData;
      // console.log("fara data");
    }
    // console.log(aux);
    const headers = new HttpHeaders({
      authorization:
        'Basic ' + utilizatorLocal,
    });

    return this._http.post(`http://localhost:4200/api/meciuri/filtrare/`, aux, {
      headers: headers,
      responseType: `json`,
    })
      .pipe(
        map<any, any[]>(
          (response) => { return response }
        ),
        catchError(error => {
          return throwError('Eroare la preluare meciuri filtrate.');
        })
      );
  };

  setIdMeci(idMeci: number) {
    localStorage.setItem("idMeci", idMeci.toString());
  };

  retrieveIdMeci(): number {
    return Number.parseInt(localStorage.getItem("idMeci") || "-1");
  };

  resetIdMeci() {
    localStorage.setItem("idMeci", "-1");
  }
}
