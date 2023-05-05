import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IAntrenor } from 'src/app/model/antrenor';
import { IEchipa } from 'src/app/model/echipa';
import { IJucator } from 'src/app/model/jucator';
import { IPremiiP } from 'src/app/model/premii-persoane';
import { ISeniori } from 'src/app/model/seniori';
import { AcasaService } from 'src/app/pagina-acasa/service/AcasaService';
import { PersonalService } from '../pagina-personal/service/PersonalService';

@Component({
  selector: 'app-seniori-modif',
  templateUrl: './seniori-modif.component.html',
  styleUrls: ['./seniori-modif.component.css']
})
export class SenioriModifComponent implements OnInit, OnDestroy {
  seniori: ISeniori = {
    'senioriDetalii' : ``,
    'senioriImagine' : ``,
    'senioriLot': ``,
    'senioriLista': []
  }
  jucatori: IJucator[] = [];
  antrenori: IAntrenor[] = [];
  premii: IPremiiP[] = [];
  roluri: Map<Date,string> = new Map<Date,string>();
  echipa1: IEchipa = {
    'csm': true,
    'id': 0,
    'logo': "",
    'nume': ""
  }
  echipa2: IEchipa = {
    'csm': true,
    'id': 0,
    'logo': "",
    'nume': ""
  }
  jucatorUpdate: IJucator = {
    'id': 0,
    'linkPoza': "",
    'nume': "",
    'prenume': "",
    'nationalitate': "",
    'dataNasterii': "",
    'inaltime': 0,
    'post': "",
    'descriere': "",
    'premii': this.premii,
    'roluri': this.roluri,
    'lotSeniori': 0,
    'echipa': this.echipa1
  }
  antrenorUpdate: IAntrenor = {
    'id': 0,
    'linkPoza': "",
    'nume': "",
    'prenume': "",
    'nationalitate': "",
    'dataNasterii': "",
    'inaltime': 0,
    'post': "",
    'descriere': "",
    'premii': this.premii,
    'roluri': this.roluri,
    'lotSeniori': 0,
    'echipa': this.echipa2
  }
  unsubscribe$: Subject<void>;

  constructor(private readonly getSeniori: AcasaService, private readonly getPersonal: PersonalService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getPersonal.getAllJucatori().subscribe((juc) => {
      this.jucatori=JSON.parse(JSON.stringify(juc));
      //console.log(this.jucatori);
    });
    this.getPersonal.getAllAntrenori().subscribe((ant) => {
      this.antrenori=JSON.parse(JSON.stringify(ant));
      //console.log(this.antrenori);
    });
  }

  CheckJuc(event: any){
    for(let i = 0; i < this.jucatori.length; i++)
    {
      if(event.target.parentElement.previousSibling.previousSibling.children[i].querySelector('input').checked == true)
      {
        this.jucatori[i].lotSeniori = 1;
        //console.log("checked" + this.jucatori[i].nume);
      }
      else
      {
        this.jucatori[i].lotSeniori = -1;
        //console.log("not checked" + this.jucatori[i].nume);
      }
      this.updateJucator(this.jucatori[i]);
    }
    
  }

  CheckAnt(event: any){
    for(let i = 0; i < this.antrenori.length; i++)
    {
      if(event.target.parentElement.previousSibling.children[i].querySelector('input').checked == true)
      {
        this.antrenori[i].lotSeniori = 1;
        //console.log("checked" + this.antrenori[i].nume);
      }
      else
      {
        this.antrenori[i].lotSeniori = -1;
        //console.log("not checked" + this.antrenori[i].nume);
      }
      this.updateAntrenor(this.antrenori[i]);
    }
  }

  updateJucator(j: IJucator){
    this.getPersonal.updateJucator(j).subscribe();
  }

  updateAntrenor(a: IAntrenor){
    this.getPersonal.updateAntrenor(a).subscribe();
  }

}
