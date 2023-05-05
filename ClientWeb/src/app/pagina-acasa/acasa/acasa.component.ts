import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IPaginare } from 'src/app/model/Paginare';
import { ISponsor } from 'src/app/model/sponsor';
import { IStire } from 'src/app/model/stire-acasa';
import { SponsoriService } from 'src/app/pagina-sponsori/service/SponsoriService';
import { AcasaService } from '../service/AcasaService';
import { DatePipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { INoutatePreviewVizitator } from 'src/app/model/noutate-preview-vizitator';
import { NoutatiServices } from 'src/app/pagina-noutati/service/NoutatiService';
import { Router, NavigationEnd} from '@angular/router';
import { IAnterior } from 'src/app/model/meci-anterior';
import { ISeniori } from 'src/app/model/seniori';
import { IEchipa } from 'src/app/model/echipa';
import { IViitor } from 'src/app/model/meci-viitor';
import { CalendarService } from 'src/app/pagina-calendar-meciuri/service/CalendarService';


@Component({
  selector: 'app-acasa',
  templateUrl: './acasa.component.html',
  styleUrls: ['./acasa.component.css']
})
export class AcasaComponent implements OnInit, OnDestroy {
  sponsori: ISponsor[] = [];
  currentRoute:string;
  stiri: IStire[] = [];
  paginaActuala: IPaginare={
    numarElemente:10,
    numarPagina:0,
  };
  echipa1: IEchipa = {
    'id': 0,
    'nume': ``,
    'logo': ``,
    'csm': false
  }
  echipa2: IEchipa = {
    'id': 0,
    'nume': ``,
    'logo': ``,
    'csm': false
  }
  mecia: IAnterior = {
    'id': 0,
    'echipa1': this.echipa1,
    'echipa2': this.echipa2,
    'scor': ``
  }

  meciv: IViitor = {
    'id': 0,
    'echipa1': this.echipa1,
    'echipa2': this.echipa2,
  }
  
  seniori: ISeniori = {
    'senioriDetalii' : ``,
    'senioriImagine' : ``,
    'senioriLot': ``,
    'senioriLista': []
  }

  unsubscribe$: Subject<void>;

  constructor(private readonly getSponsori:SponsoriService, private readonly getAcasa:AcasaService,private router: Router,private readonly CalendarMeciuri:CalendarService) { 
    this.unsubscribe$ = new Subject<void>();
    this.currentRoute = this.router.url;
  }

  ngOnInit(): void {
    this.getSpon();
    this.getStiri();
    this.getSeniori();
    this.getMeciAnterior();
    this.getMeciViitor();
    localStorage.setItem("pagina-actuala","acasa");
  }

  getSpon(){
    this.getSponsori.getSponsori().subscribe((sponsori) => {
      this.sponsori = JSON.parse(JSON.stringify(sponsori));
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  getStiri(){
    this.getAcasa.getNoutati(this.paginaActuala).subscribe((stiri) => {
      this.stiri=JSON.parse(JSON.stringify(stiri));
    })
  }

  accesareMeci(idMeci: number)
  {
    this.CalendarMeciuri.setIdMeci(idMeci);
    this.router.navigate([`/calendar/meciDetaliat`]);
  }

  accesareNoutate(event: Event, idStire:number)
  {
    localStorage.setItem("noutateaDetaliata",idStire.toString());
    this.router.navigate([`/noutati/noutateDetailed`]);
  }

  getSeniori(){
    this.getAcasa.getSeniori().subscribe((senior) => {
      this.seniori = JSON.parse(JSON.stringify(senior));
      //console.dir(this.seniori);
    })
  }

  getSenioriDetalii(){
    this.router.navigate([`/acasa/seniori`]);
  }

  getMeciAnterior(){
    this.getAcasa.getMeciAnterior().subscribe((meci) => {
      this.mecia=JSON.parse(JSON.stringify(meci));
      //console.log("meci anterior");
      //console.log(this.mecia);
    });
  }

  getMeciViitor(){
    this.getAcasa.getMeciViitor().subscribe((meci) => {  
      this.meciv=JSON.parse(JSON.stringify(meci));      
      //console.log("meci viitor");
      //console.log(this.meciv);
    })
  }


}
