import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { CalendarService } from '../../service/CalendarService';

@Component({
  selector: 'app-admin-calendar-meciuri',
  templateUrl: './admin-calendar-meciuri.component.html',
  styleUrls: ['./admin-calendar-meciuri.component.css']
})
export class AdminCalendarMeciuriComponent implements OnInit, OnDestroy {

  afisareDiv: boolean = true;
  afisareMec: boolean = false;
  afisareCam: boolean = false;
  afisareEch: boolean = false;
  afisareLoc: boolean = false;
  // afisPag1: boolean = true;
  // afisPag2: boolean = true;
  // afisPag3: boolean = false;
  // afisPag4: boolean = false;

  unsubscribe$: Subject<void>;
  afisbtnModifica: boolean = false;
  afisbtnAdauga: boolean = false;
  constructor(private readonly getCalendar:CalendarService) {
    this.unsubscribe$ = new Subject<void>();
   }

  ngOnInit(): void {
  }

  afisareDivizii():void {
    this.afisareDiv = true;
    this.afisareMec = false;
    this.afisareCam = false;
    this.afisareEch = false;
    this.afisareLoc = false;
    document.getElementById("btn-divizii")?.style.setProperty('color', '#0D1760');
    document.getElementById("btn-meciuri")?.style.setProperty('color', 'white');
    document.getElementById("btn-campionate")?.style.setProperty('color', 'white');
    document.getElementById("btn-echipe")?.style.setProperty('color', 'white');
    document.getElementById("btn-locatii")?.style.setProperty('color', 'white');
    // this.afisPag1=false;
    // this.afisPag2=false;
    // this.afisPag3=false;
    // this.afisPag4=false;
  }

  afisareMeciuri():void {
    this.afisareDiv = false;
    this.afisareMec = true;
    this.afisareCam = false;
    this.afisareEch = false;
    this.afisareLoc = false;
    document.getElementById("btn-divizii")?.style.setProperty('color', 'white');
    document.getElementById("btn-meciuri")?.style.setProperty('color', '#0D1760');
    document.getElementById("btn-campionate")?.style.setProperty('color', 'white');
    document.getElementById("btn-echipe")?.style.setProperty('color', 'white');
    document.getElementById("btn-locatii")?.style.setProperty('color', 'white');
    // this.afisPag1=false;
    // this.afisPag2=false;
    // this.afisPag3=false;
    // this.afisPag4=false;
  }

  afisareCampionate():void {
    this.afisareDiv = false;
    this.afisareMec = false;
    this.afisareCam = true;
    this.afisareEch = false;
    this.afisareLoc = false;
    document.getElementById("btn-divizii")?.style.setProperty('color', 'white');
    document.getElementById("btn-meciuri")?.style.setProperty('color', 'white');
    document.getElementById("btn-campionate")?.style.setProperty('color', '#0D1760');
    document.getElementById("btn-echipe")?.style.setProperty('color', 'white');
    document.getElementById("btn-locatii")?.style.setProperty('color', 'white');
    // this.afisPag1=false;
    // this.afisPag2=false;
    // this.afisPag3=false;
    // this.afisPag4=false;
  }

  afisareEchipe():void {
    this.afisareDiv = false;
    this.afisareMec = false;
    this.afisareCam = false;
    this.afisareEch = true;
    this.afisareLoc = false;
    document.getElementById("btn-divizii")?.style.setProperty('color', 'white');
    document.getElementById("btn-meciuri")?.style.setProperty('color', 'white');
    document.getElementById("btn-campionate")?.style.setProperty('color', 'white');
    document.getElementById("btn-echipe")?.style.setProperty('color', '#0D1760');
    document.getElementById("btn-locatii")?.style.setProperty('color', 'white');
    // this.afisPag1=false;
    // this.afisPag2=false;
    // this.afisPag3=false;
    // this.afisPag4=false;
  }

  afisareLocatii():void {
    this.afisareDiv = false;
    this.afisareMec = false;
    this.afisareCam = false;
    this.afisareEch = false;
    this.afisareLoc = true;
    document.getElementById("btn-divizii")?.style.setProperty('color', 'white');
    document.getElementById("btn-meciuri")?.style.setProperty('color', 'white');
    document.getElementById("btn-campionate")?.style.setProperty('color', 'white');
    document.getElementById("btn-echipe")?.style.setProperty('color', 'white');
    document.getElementById("btn-locatii")?.style.setProperty('color', '#0D1760');
    // this.afisPag1=false;
    // this.afisPag2=false;
    // this.afisPag3=false;
    // this.afisPag4=false;
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  AfisareModifica(){
    this.afisbtnModifica = true;
    this.afisbtnAdauga = false;
  }

  AfisareAdauga(){
    this.afisbtnModifica = false;
    this.afisbtnAdauga = true;
  }

}
