import { Component, OnDestroy, OnInit } from '@angular/core';
import { IJucator } from 'src/app/model/jucator';
import { IAntrenor } from 'src/app/model/antrenor';
import { Subject } from 'rxjs';
import { PersonalService } from '../service/PersonalService';
import { IPaginare } from 'src/app/model/Paginare';

@Component({
  selector: 'app-personal',
  templateUrl: './personal.component.html',
  styleUrls: ['./personal.component.css']
})
export class PersonalComponent implements OnInit, OnDestroy {
  antrenori: IAntrenor[] = [];
  jucatori: IJucator[] = [];
  private valoare: string = ``;
  paginaActuala1: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  totalPagini1: number = 0;
  paginaActuala2: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  paginaActuala3: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  paginaActuala4: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  totalPagini2: number = 0;
  totalPagini3: number = 0;
  totalPagini4: number = 0;
  afisareAnt: boolean = true;
  afisareJuc: boolean = false;
  afisPag1: boolean = true;
  afisPag2: boolean = true;
  afisPag3: boolean = false;
  afisPag4: boolean = false;

  unsubscribe$: Subject<void>;

  afisareAntrenori():void {
    this.afisareAnt = true;
    this.afisareJuc = false;
    document.getElementById("btn-antrenori")?.style.setProperty('color', '#0D1760');
    document.getElementById("btn-jucatori")?.style.setProperty('color', 'white');
    this.PAntrenori(); 
    this.afisPag1=true;
    this.afisPag2=true;  
    this.afisPag3=false;
    this.afisPag4=false; 
  }

  afisareJucatori():void {
    this.afisareJuc = true;
    this.afisareAnt = false;
    document.getElementById("btn-jucatori")?.style.setProperty('color', '#0D1760');
    document.getElementById("btn-antrenori")?.style.setProperty('color', 'white');
    this.PJucatori();
    this.afisPag1=true;
    this.afisPag2=true;
    this.afisPag3=false;
    this.afisPag4=false;
  }

  constructor(private readonly getJucatori: PersonalService, private readonly getAntrenori: PersonalService) {
    this.unsubscribe$ = new Subject<void>();
    this.jucatori;
    this.antrenori;
   }

  ngOnInit(): void {
    this.getJucatori.getJucatori().subscribe((nrPag1) => {
      this.totalPagini1=nrPag1;
      //console.dir("totalpagini1 = " + this.totalPagini1);
    });
    this.getAntrenori.getAntrenori().subscribe((nrPag2) => {
      this.totalPagini2=nrPag2;
      //console.dir("totalpagini2 = " + this.totalPagini2);
    });
    document.getElementById("btn-antrenori")?.style.setProperty('color', '#0D1760');
    document.getElementById("btn-jucatori")?.style.setProperty('color', 'white'); 
    this.PJucatori();
    this.PAntrenori();
    this.afisPag1=true;
    this.afisPag2=true;
    this.afisPag3=false;
    this.afisPag4=false;
    //console.dir(this.valoare);
    localStorage.setItem("pagina-actuala","personal");
  }

  ngOnDestroy(): void {
      this.unsubscribe$.next();
      this.unsubscribe$.complete();
  }

  PageJuc(event: Event, nrPag1:number){
    this.paginaActuala1.numarPagina=nrPag1;
    this.PJucatori();
  }

  PageAnt(event: Event, nrPag2:number){
    this.paginaActuala2.numarPagina=nrPag2;
    this.PAntrenori();
  }

  PageFiltruJuc(event: Event, nrPag3:number){
    this.paginaActuala3.numarPagina=nrPag3;
    this.AfisareJucatoriFiltru();
  }

  PageFiltruAnt(event: Event, nrPag4:number){
    this.paginaActuala4.numarPagina=nrPag4;
    this.AfisareAntrenoriFiltru();
  }

  PJucatori(){
    this.getJucatori.getJucatoriPag(this.paginaActuala1).subscribe((jucatori)=>{
      this.jucatori=JSON.parse(JSON.stringify(jucatori));
    })
    //console.dir(this.jucatori);
  }

  PAntrenori(){
    this.getAntrenori.getAntrenoriPag(this.paginaActuala2).subscribe((antrenori)=>{
      this.antrenori=JSON.parse(JSON.stringify(antrenori));
    })
    //console.dir(this.antrenori);
  }

  FiltrareValoare(event: any){
    this.valoare = event.target.value;
    if(this.valoare == ``)
    {
      this.PJucatori();
      this.PAntrenori();
      if(this.afisareAnt == true)
      {
        document.getElementById("btn-antrenori")?.style.setProperty('color', '#0D1760');
      }
      if(this.afisareJuc == true)
      {
        document.getElementById("btn-jucatori")?.style.setProperty('color', '#0D1760');
      }
      this.afisPag1=true;
      this.afisPag2=true;
      this.afisPag3=false;
      this.afisPag4=false;
    }
    
    //console.dir(this.valoare);
    
  }

  AfisarePersonalFiltru(){
    //console.log("afisAnt = " + this.afisareAnt);
    //console.log("afisJuc = " + this.afisareJuc);
    if(this.afisareAnt == true)
    {
      this.AfisareAntrenoriFiltru();
    }
    if(this.afisareJuc == true)
    {
      this.AfisareJucatoriFiltru();
    }
  }

  AfisareJucatoriFiltru(){
    this.getJucatori.getJucatoriFiltru(this.valoare,this.paginaActuala3).subscribe((jucatori)=>{
      this.jucatori=JSON.parse(JSON.stringify(jucatori["jucatori"]));
      //console.dir(jucatori);
      this.afisPag1=false;
      this.afisPag2=false;
      this.afisPag3=true;
      this.afisPag4=false;
    })
  }

  AfisareAntrenoriFiltru(){
    this.getAntrenori.getAntrenoriFiltru(this.valoare,this.paginaActuala4).subscribe((antrenori)=>{
      this.antrenori=JSON.parse(JSON.stringify(antrenori["antrenori"]));
      //console.dir(antrenori);
      this.afisPag1=false;
      this.afisPag2=false;
      this.afisPag3=false;
      this.afisPag4=true;
    })
  }

}
