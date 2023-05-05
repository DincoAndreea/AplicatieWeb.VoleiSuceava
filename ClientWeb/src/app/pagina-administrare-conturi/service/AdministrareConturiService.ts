import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map, catchError, throwError } from "rxjs";
import { ICreatorDeContinut } from "src/app/model/creatorDeContinut";
import { AutentificareService } from "src/app/pagina-autentificare/service/AutentificareService";

@Injectable({ providedIn: `root` })
export class AdministrareConturiService {
  
  constructor(    private _http: HttpClient,
    private autentificare: AutentificareService
  ) { }

  getCreatori(): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.get('http://localhost:4200/api/utilizatori/', {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la preluarea creatorilor de continut.');
      })
    );
  };

  addCreator(creator:ICreatorDeContinut): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.post('http://localhost:4200/api/utilizatori/inregistrare/', creator, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la adaugarea creatorului de continut.');
      })
    );
  };

  updateCreator(creator: ICreatorDeContinut): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.put(`http://localhost:4200/api/utilizatori/administrare/${creator.nume}`, creator, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la actualizarea creatorului de continut.');
      })
    );
  };

  deleteCreator(creatorId: number): Observable<any> {
    let utilziatorLocal = this.autentificare.getUtilizatorActiv();

    const headers = new HttpHeaders({
      authorization: 'Basic ' + utilziatorLocal,
    });

    return this._http.delete(`http://localhost:4200/api/utilizatori/administrare/${creatorId}`, {
      headers: headers,
    }).pipe(
      map<any, any[]>(
        (response) => { return response }
      ),
      catchError(error => {
        return throwError('Eroare la stergerea creatorului de continut.');
      })
    );
  };
  
}