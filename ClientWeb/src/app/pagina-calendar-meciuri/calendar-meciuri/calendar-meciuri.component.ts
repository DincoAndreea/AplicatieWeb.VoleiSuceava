import { Component, OnDestroy, OnInit } from '@angular/core';
import { CalendarService } from '../service/CalendarService';
import { IDivizii } from 'src/app/model/divizii';
import { IMeciFiltruCuData } from 'src/app/model/meci-filtruCuData';
import { IMeciFiltruFaraData } from 'src/app/model/meci-filtruFaraData';
import { Router } from '@angular/router';
import { JsonpInterceptor } from '@angular/common/http';
import { Subject } from 'rxjs';
import { IMeciFiltruFaraDataPaginat } from 'src/app/model/meci-filtruFaraDataPaginat';
import { IMeciFiltruCuDataPaginat } from 'src/app/model/meci-filtruCuDataPaginat';

@Component({
  selector: 'app-calendar-meciuri',
  templateUrl: './calendar-meciuri.component.html',
  styleUrls: ['./calendar-meciuri.component.css']
})
export class CalendarMeciuriComponent implements OnInit, OnDestroy {
  divizii: IDivizii[] = [];

  unsubscribe$: Subject<void>;

  filtruDivizie: number = -1;
  setatDataStart: boolean = false;
  setatDataStop: boolean = false;
  dataStart: Date = new Date();
  dataStop: Date = new Date();

  constructor(private readonly CalendarMeciuri: CalendarService, private router: Router) {
    this.unsubscribe$ = new Subject<void>();
  }


  ngOnInit(): void {
    this.afisareDivizii();
    localStorage.setItem("pagina-actuala", "calendar");
    this.CalendarMeciuri.resetFiltruCuDataPaginat();
    this.CalendarMeciuri.resetFiltruFaraDataPaginat();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  afisareDivizii() {
    this.CalendarMeciuri.getDivizii().subscribe((divizie) => {
      this.divizii = JSON.parse(JSON.stringify(divizie))
    })
  }
  setDivizieFiltru(event:any, divizieId: number) {
    //console.log("id divizie: "+divizieId);
    try{
    document.getElementsByClassName("selectedDivizie")[0].className="";
    }
    catch(error){
      //console.log(error);
      this.filtruDivizie = divizieId;
    }
    event.srcElement.classList.add("selectedDivizie");
    this.filtruDivizie = divizieId;
  }
  actualizareDataStart() {
    let aux = document.getElementById("dataStart");
    this.dataStart = new Date((<HTMLInputElement>aux).value || new Date());
    // console.log(this.dataStart);
    this.setatDataStart = true;
  }
  actualizareDataStop() {
    let aux = document.getElementById("dataStop");
    this.dataStop = new Date((<HTMLInputElement>aux).value || new Date());
    // console.log(this.dataStop);
    this.setatDataStop = true;
  }
  populareArhivaFolosindId(index: number): string {
    let aux = new Date();
    let yearToNumber = Number.parseInt(aux.getFullYear().toString()) - index;
    let yearToString = (yearToNumber - 1).toString() + "/" + yearToNumber.toString();
    return yearToString;
  }
  actualizareDataDinArhiva(index: number) {
    this.setatDataStart = true;
    this.setatDataStop = true;
    this.dataStart.setFullYear(Number.parseInt(new Date().getFullYear().toString()) - (index + 1), 0, 1);//an luna zi
    this.dataStart.setHours(0);
    this.dataStart.setMinutes(0, 0, 0);
    let aux = (Number.parseInt(new Date().getFullYear().toString()) - (index + 1)).toString() + "-01-01T00:00";
    (<HTMLInputElement>document.getElementById("dataStart")).value = aux;
    this.dataStop.setFullYear(Number.parseInt(new Date().getFullYear().toString()) - (index), 11, 31);//an luna zi
    this.dataStop.setHours(0);
    this.dataStop.setMinutes(0, 0, 0);
    aux = (Number.parseInt(new Date().getFullYear().toString()) - (index)).toString() + "-12-31T23:59";
    (<HTMLInputElement>document.getElementById("dataStop")).value = aux;
  }
  redirectareCatrePreview(event:any, tipMeci: string) {
    // console.log("tip meci "+ tipMeci);
    if (this.setatDataStart == false || this.setatDataStop == false) {
      // console.log("store filtru fara data");
      let aux: IMeciFiltruFaraDataPaginat = {
        'idDivizie': this.filtruDivizie,
        'tipMeci': tipMeci,
        'paginareDto': {
          'numarElemente':6,
          'numarPagina':0,
        }
      }
      this.CalendarMeciuri.storeFiltru(undefined,aux);

    }
    else {
      // console.log("store filtru cu data");
      let aux: IMeciFiltruCuDataPaginat = {
        'idDivizie': this.filtruDivizie,
        'tipMeci': tipMeci,
        'dataIncepere': this.dataStart,
        'dataSfarsit': this.dataStop,
        'paginareDto': {
          'numarElemente':6,
          'numarPagina':0,
        }
      }
      // console.log(aux);
      this.CalendarMeciuri.storeFiltru(aux,undefined);
    }
    this.router.navigate([`/calendar/meciuriPreview`]);
  }
}
