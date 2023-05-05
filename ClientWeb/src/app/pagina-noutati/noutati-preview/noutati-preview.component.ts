import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { INoutatePreviewVizitator } from 'src/app/model/noutate-preview-vizitator';
import { NoutatiServices } from '../service/NoutatiService';
import { IPaginare } from 'src/app/model/Paginare';
import { Router, NavigationEnd} from '@angular/router';

@Component({
  selector: 'app-noutati-preview',
  templateUrl: './noutati-preview.component.html',
  styleUrls: ['./noutati-preview.component.css']
})
export class NoutatiPreviewComponent implements OnInit, OnDestroy {
  currentRoute:string;
  unsubscribe$: Subject<void>;
  noutati: INoutatePreviewVizitator[] = [];
  paginaActuala: IPaginare={
    numarElemente:6,
    numarPagina:0,
  };
  totalPagini: number=0;

  
  constructor(private readonly noutatiServices: NoutatiServices, private router: Router) { 
    this.unsubscribe$ = new Subject<void>;
    this.currentRoute = this.router.url;
  }
  ngOnInit(): void {    
    this.noutatiServices.getPaginiPostate().subscribe((nrPag) => {
      //console.log(nrPag);
      this.totalPagini=nrPag;
      localStorage.setItem("pagina-actuala","noutati-preview");
    });
    this.populareStiri();
  }

  ngOnDestroy(): void{
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  goToPage(event: Event, nrPag:number){
    this.paginaActuala.numarPagina=nrPag;
    this.populareStiri();
    //console.dir(event);
  }
  
  accesareNoutate(event: Event, idStire:number)
  {
    //console.log(idStire);
    localStorage.setItem("noutateaDetaliata",idStire.toString());
    document.location = this.router.url+`/noutateDetailed`;
  }

  populareStiri()
  {
    this.noutatiServices.getNoutatiPostate(this.paginaActuala).subscribe((noutati)=>{
      this.noutati=JSON.parse(JSON.stringify(noutati));
      //console.dir(noutati);
      for(var noutate of this.noutati)
      {
        if(noutate.imagine=="")
          noutate.imagine="assets/newspaper.png";
      }
    })
  }


}
