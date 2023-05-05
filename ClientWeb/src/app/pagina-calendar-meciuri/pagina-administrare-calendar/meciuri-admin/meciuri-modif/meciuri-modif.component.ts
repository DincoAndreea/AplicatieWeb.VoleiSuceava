import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ICampionat } from 'src/app/model/campionat';
import { IDivizii } from 'src/app/model/divizii';
import { IEchipa } from 'src/app/model/echipa';
import { IJucator } from 'src/app/model/jucator';
import { IJudet } from 'src/app/model/judet';
import { ILocalitate } from 'src/app/model/localitate';
import { IMeciA } from 'src/app/model/meci-adaugare';
import { IMeciC } from 'src/app/model/meci-complet';
import { IPaginare } from 'src/app/model/Paginare';
import { ITara } from 'src/app/model/tara';
import { CalendarService } from 'src/app/pagina-calendar-meciuri/service/CalendarService';
import { PersonalService } from 'src/app/pagina-personal/pagina-personal/service/PersonalService';

@Component({
  selector: 'app-meciuri-modif',
  templateUrl: './meciuri-modif.component.html',
  styleUrls: ['./meciuri-modif.component.css']
})
export class MeciuriModifComponent implements OnInit, OnDestroy {

  echipa1: IEchipa = {
    'id': -2,
    'logo': "",
    'nume': "",
    'csm': false
  }
  echipa2: IEchipa = {
    'id': -2,
    'logo': "",
    'nume': "",
    'csm': false
  }
  tara: ITara = {
    'id': -2,
    'tara': ""
  }
  judet: IJudet = {
    'id': -2,
    'judet': "",
    'tara': this.tara
  }
  localitate: ILocalitate = {
    'id': -2,
    'judet': this.judet,
    'localitate': ""
  }
  divizie: IDivizii = {
    'id': -2,
    'nume': ""
  }
  campionat: ICampionat = {
    'id': -2,
    'dataIncepere': new Date(),
    'dataSfarsit': new Date(),
    'denumire': "",
    'localitate': this.localitate
  }
  meciUpdate: IMeciA = {
    'id': 0,
    'scor': [],
    'link': "",
    'idDivizie': -2,
    'idCampionat': -2,
    'echipe': [this.echipa1, this.echipa2],
    'locatie': this.localitate,
    'dataMeci': new Date()
  }
  meciuri: IMeciC[] = [];
  paginaActuala2: IPaginare = {
    numarElemente: 4,
    numarPagina: 0
  };
  totalPagini2: number = 0;
  divizii: IDivizii[] = [];
  echipa: IEchipa[] = [];
  campionate: ICampionat[] = [];
  locatii: ILocalitate[] = [];
  unsubscribe$: Subject<void>;
  constructor(private readonly getCalendar:CalendarService, private readonly getEchipe:PersonalService) { 
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.getCalendar.getMeciuri(this.paginaActuala2).subscribe((meciuri) => {
      this.totalPagini2=JSON.parse(JSON.stringify(meciuri["numarPagini"]))
      //console.dir(this.meciuri);
    })
    this.afisareMeciuri();
    this.afisareCampionate();
    this.afisareDivizii();
    this.afisareEchipe();
    this.afisareLocatii();
  }

  afisareMeciuri(){
    this.getCalendar.getMeciuri(this.paginaActuala2).subscribe((meciuri) => {
      this.meciuri=JSON.parse(JSON.stringify(meciuri["meciuri"]))
      //console.dir(this.meciuri);
    })
  }

  afisareDivizii(){
    this.getCalendar.getDivizii().subscribe((divizie) => {
      this.divizii = JSON.parse(JSON.stringify(divizie))
      //console.dir(this.divizii);
    })
  }

  afisareEchipe(){
    this.getEchipe.getEchipe().subscribe((ech) => {
      this.echipa=JSON.parse(JSON.stringify(ech));
      //console.log(this.echipa);
    })
  }

  afisareCampionate(){
    this.getCalendar.getCampionate().subscribe((campionate) => {
      this.campionate=JSON.parse(JSON.stringify(campionate));
      //console.dir(this.campionate);
    })
  }

  afisareLocatii(){
    this.getCalendar.getLocatii().subscribe((locatii) => {
      this.locatii=JSON.parse(JSON.stringify(locatii));
      //console.dir(this.locatii);
    })
  }

  PageAnt(event: Event, nrPag2:number){
    this.paginaActuala2.numarPagina=nrPag2;
    this.afisareMeciuri();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  Modifica(scor1: number, scor2: number, link: string, id: number,event: any){
    this.meciUpdate.id = id;
    this.meciUpdate.scor[0] = Number.isNaN(scor1) ? -1 : scor1;
    this.meciUpdate.scor[1] = Number.isNaN(scor2) ? -1 : scor2;
    this.meciUpdate.link = link;
    this.meciUpdate.dataMeci = event.target.parentElement.previousSibling.children[2].querySelector('input').value;
    for(let i = 0; i < this.divizii.length; i++)
    {
      if(event.target.parentElement.previousSibling.children[4].querySelector('select').options[i].selected)
      {
        this.meciUpdate.idDivizie = this.divizii[event.target.parentElement.previousSibling.children[4].querySelector('select').options[i].index].id;
      }
    }
    for(let i = 0; i < this.locatii.length; i++)
    {
      if(event.target.parentElement.previousSibling.children[5].querySelector('select').options[i].selected)
      {
        this.meciUpdate.locatie = this.locatii[event.target.parentElement.previousSibling.children[5].querySelector('select').options[i].index];
      }
    }
    for(let i = 0; i < this.echipa.length; i++)
    {
      if(event.target.parentElement.previousSibling.children[6].querySelector('select').options[i].selected)
      {
        this.meciUpdate.echipe[0] = this.echipa[event.target.parentElement.previousSibling.children[6].querySelector('select').options[i].index];
      }
    }
    for(let i = 0; i < this.echipa.length; i++)
    {
      if(event.target.parentElement.previousSibling.children[7].querySelector('select').options[i].selected)
      {
        this.meciUpdate.echipe[1] = this.echipa[event.target.parentElement.previousSibling.children[7].querySelector('select').options[i].index];
      }
    }
    if(event.target.parentElement.previousSibling.children[8].querySelector('select').options[this.campionate.length].selected)
    {
      this.meciUpdate.idCampionat = -1;
    }
    else
    {
      for(let i = 0; i < this.campionate.length; i++)
      {
        if(event.target.parentElement.previousSibling.children[8].querySelector('select').options[i].selected)
        {
          this.meciUpdate.idCampionat = this.campionate[event.target.parentElement.previousSibling.children[8].querySelector('select').options[i].index].id;
        }
      }
    }
    //console.log(this.meciUpdate);
    let okk = 0;
    //console.log(this.meciUpdate);
    let dataa = new Date(this.meciUpdate.dataMeci);
    
    let today = new Date();
    if(dataa > today)
    {
      okk = 1;
    }
    if(((this.meciUpdate.idCampionat > 0 && this.meciUpdate.idDivizie > 0) || (this.meciUpdate.idCampionat < 0 && this.meciUpdate.idDivizie > 0)) && this.checkDate(this.meciUpdate.dataMeci) && this.meciUpdate.locatie.id != -2 && this.meciUpdate.echipe[0].id != -2 && this.meciUpdate.echipe[1].id != -2 && okk == 0)
    {
      this.getCalendar.updateMeci(this.meciUpdate).subscribe();
      alert("Meciul a fost modificat cu succes.");
    }
    else
    {
      if((this.meciUpdate.scor[0] > -1 && this.meciUpdate.scor[1] > -1) || (this.meciUpdate.scor[0] > -1 && this.meciUpdate.scor[1] == -1) || (this.meciUpdate.scor[0] == -1 && this.meciUpdate.scor[1] > -1) && okk == 1)
      {
        alert("Un meci din viitor nu poate avea scor.");
      }
      if(this.checkDate(this.meciUpdate.dataMeci) == false)
      {
        alert("Data meciului este invalidă.");
      }
      if(this.meciUpdate.idCampionat == -2)
      {
        alert("Alegeți un campionat");
      }
      if(this.meciUpdate.idDivizie == -2)
      {
        alert("Alegeți o divizie.");
      }
      if(this.meciUpdate.echipe[0].id == -2)
      {
        alert("Alegeți echipa 1.");
      }
      if(this.meciUpdate.echipe[1].id == -2)
      {
        alert("Alegeți echipa 2.");
      }
      if(this.meciUpdate.locatie.id == -2)
      {
        alert("Alegeți o locație.");
      }
    }
  }

  Sterge(meci: IMeciC){
    if(this.getCalendar.deleteMeci(meci).subscribe())
    {
      alert("Meciul a fost șters cu succes.");
    }
    else
    {
      alert("Nu s-a putut șterge meciul.");
    }
    
  }

  checkDate(date: Date): boolean{
    let d1 = new Date(date).getFullYear();
    return d1 > 1930 && d1 < (d1 + 2);
  }

}
