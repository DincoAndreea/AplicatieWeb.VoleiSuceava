import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { IMeciFiltruCuData } from 'src/app/model/meci-filtruCuData';
import { IMeciPrevizualizare } from 'src/app/model/meci-previzualizare';
import { IEchipa } from 'src/app/model/echipa';
import { CalendarService } from '../service/CalendarService';
import { IMeciPrevizualizarePaginat } from 'src/app/model/meci-previzualizarePaginat';
import { IPaginare } from 'src/app/model/Paginare';

@Component({
  selector: 'app-calendar-meciuri-preview',
  templateUrl: './calendar-meciuri-preview.component.html',
  styleUrls: ['./calendar-meciuri-preview.component.css']
})
export class CalendarMeciuriPreviewComponent implements OnInit, OnDestroy {
  unsubscribe$: Subject<void>;

  paginaActuala: IPaginare={
    numarElemente:6,
    numarPagina:0,
  };

  listaMeciuriPrevizualizate:IMeciPrevizualizare[]=[];
  meciPrevizualizare: IMeciPrevizualizare = {
    'id': -1,
    echipa1: {
      'id': -1,
      'nume': "",
      'logo': "",
      'csm': true
    },
    echipa2: {
      'id': -1,
      'nume': "",
      'logo': "",
      'csm': true
    },
    scor: '',
    dataMeci: new Date(),
    oraMeci: new Date(),
    castigator: {
      'id': -1,
      'nume': "",
      'logo': "",
      'csm': true
    }
  }
  meciPrevizualizarePaginat:IMeciPrevizualizarePaginat={
    meciuri:this.listaMeciuriPrevizualizate,
    numarPagini:0
  }

  constructor(private readonly CalendarMeciuri: CalendarService, private router: Router) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    //console.log("onInit la preview meciuri");
    this.CalendarMeciuri.resetIdMeci();
    let auxData = this.CalendarMeciuri.retrieveFiltruCuDataPaginat();
    let auxNoData=this.CalendarMeciuri.retrieveFiltruFaraDataPaginat();
    if(auxData.idDivizie!=-10)
    {
      auxData.paginareDto.numarPagina=0;
      this.CalendarMeciuri.storeFiltru(auxData, undefined);
    }
    if(auxNoData.idDivizie!=-10)
    {
      auxNoData.paginareDto.numarPagina=0;
      this.CalendarMeciuri.storeFiltru(undefined, auxNoData);
    }
    this.populareMeciuri();
  }
  populareMeciuri(){
    let aux = this.CalendarMeciuri.retrieveFiltruCuDataPaginat();
    if (aux.idDivizie == -10) {
      this.CalendarMeciuri.getMeciuriFiltratePaginate(undefined, this.CalendarMeciuri.retrieveFiltruFaraDataPaginat()).subscribe((meci) => {
        //  console.log("meciuri preluate din db");
        //  console.log(meci);
        this.meciPrevizualizarePaginat=JSON.parse(JSON.stringify(meci));
        //  console.log(this.meciPrevizualizarePaginat);
      });
    }
    else {
      this.CalendarMeciuri.getMeciuriFiltratePaginate(aux, undefined).subscribe((meci) => {
        // console.log(JSON.parse(JSON.stringify(meci)));
        this.meciPrevizualizarePaginat=JSON.parse(JSON.stringify(meci));
        //this.paginaActuala.numarPagina=this.meciPrevizualizarePaginat.numarPagini;
      });
    }
  }
  acceseazaMeci(id: number) {
    this.CalendarMeciuri.setIdMeci(id);
    this.router.navigate([`/calendar/meciDetaliat`]);
  }

  inapoi(){
    this.router.navigate([`/calendar/`]);
  }

  goToPage(event: Event, nrPag:number){
    this.paginaActuala.numarPagina=nrPag;
    //sa stochez in local storage pagina si sa dau populare iar.
    let auxData = this.CalendarMeciuri.retrieveFiltruCuDataPaginat();
    let auxNoData=this.CalendarMeciuri.retrieveFiltruFaraDataPaginat();
    if(auxData.idDivizie!=-10)
    {
      auxData.paginareDto=this.paginaActuala;
      this.CalendarMeciuri.storeFiltru(auxData, undefined);
    }
    if(auxNoData.idDivizie!=-10)
    {
      auxNoData.paginareDto=this.paginaActuala;
      this.CalendarMeciuri.storeFiltru(undefined, auxNoData);
    }
    this.populareMeciuri();
    //console.dir(event);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
