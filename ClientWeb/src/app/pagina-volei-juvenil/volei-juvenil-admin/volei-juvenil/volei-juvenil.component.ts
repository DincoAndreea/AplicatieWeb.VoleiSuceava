import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { identifierName } from '@angular/compiler';
import { VoleiJuvenilService } from '../service/VoleiJuvenil';
import { CadetiService } from '../../pagina-cadeti/service/CadetiService';
import { JunioriService } from '../../pagina-juniori/service/JunioriService';
import { MinivoleiService } from '../../pagina-mini-volei/service/MinivoleiService';
import { SperanteService } from '../../pagina-sperante/service/SperanteService';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { ICadeti } from 'src/app/model/cadeti';
import { IJuniori } from 'src/app/model/juniori';
import { IMinivolei } from 'src/app/model/minivolei';
import { ISperante } from 'src/app/model/sperante';

@Component({
  selector: 'app-volei-juvenil',
  templateUrl: './volei-juvenil.component.html',
  styleUrls: ['./volei-juvenil.component.css']
})
export class VoleiJuvenilComponent implements OnInit, OnDestroy {

  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  cadeti: ICadeti[] = [];
  juniori: IJuniori[] = [];
  minivolei: IMinivolei[] = [];
  sperante: ISperante[] = [];
  cadet: ICadeti = {
    'id': 0,
    'imagineLot': "",
    'lot': "",
    'informatii': "",
    'premiiCadeti': []
  }
  prc: IPremiiLot[] = [];
  junior: IJuniori = {
    'id': 0,
    'imagineLot': "",
    'lot': "",
    'informatii': "",
    'premiiJuniori': []
  }
  prj: IPremiiLot[] = [];
  miniv: IMinivolei = {
    'id': 0,
    'imagineLot': "",
    'lot': "",
    'informatii': "",
    'premiiMinivolei': []
  }
  prm: IPremiiLot[] = [];
  speranta: ISperante = {
    'id': 0,
    'imagineLot': "",
    'lot': "",
    'informatii': "",
    'premiiSperante': []
  }
  prs: IPremiiLot[] = [];

  afisCadeti: boolean = false;
  afisJuniori: boolean = false;
  afisMinivolei: boolean = false;
  afisSperante: boolean = false;
  afisbtnAdauga: boolean = false;
  afisbtnModifica: boolean = false;

  unsubscribe$: Subject<void>;
  
  constructor(private readonly getCadeti: CadetiService, private readonly getJuniori: JunioriService, private readonly getSperante: SperanteService, private readonly getMinivolei: MinivoleiService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    /*console.dir("afisbtnAdauga " + this.afisbtnAdauga);
    console.dir("afisbtnModifica " + this.afisbtnModifica);
    console.dir("afisCadeti " + this.afisCadeti);
    console.dir("afisJuniori " + this.afisJuniori);
    console.dir("afisMinivolei " + this.afisMinivolei);
    console.dir("afisSperante " + this.afisSperante);*/
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  AfisareCadeti(){
    this.afisCadeti = true;
    this.afisJuniori = false;
    this.afisMinivolei = false;
    this.afisSperante = false;
  }

  AfisareJuniori(){
    this.afisCadeti = false;
    this.afisJuniori = true;
    this.afisMinivolei = false;
    this.afisSperante = false;
  }

  AfisareMinivolei(){
    this.afisCadeti = false;
    this.afisJuniori = false;
    this.afisMinivolei = true;
    this.afisSperante = false;
  }

  AfisareSperante(){
    this.afisCadeti = false;
    this.afisJuniori = false;
    this.afisMinivolei = false;
    this.afisSperante = true;
  }

  AfisareModifica(){
    this.afisbtnModifica = true;
    this.afisbtnAdauga = false;
  }

  AfisareAdauga(){
    this.afisbtnModifica = false;
    this.afisbtnAdauga = true;
  }

  addRow(event: any): void {
    var table = event.target.parentElement.parentElement.firstElementChild.firstElementChild;
    var row = document.createElement("tr");
    var c1 = document.createElement("td");
    var c2 = document.createElement("td");
    var c3 = document.createElement("td");
    var c4 = document.createElement("td");
    c1.style.setProperty("text-align","center");
    c1.style.setProperty("border","1px solid #262F6C");
    c1.style.setProperty("padding","12px");
    c2.style.setProperty("text-align","center");
    c2.style.setProperty("border","1px solid #262F6C");
    c2.style.setProperty("padding","12px");
    c3.style.setProperty("text-align","center");
    c3.style.setProperty("border","1px solid #262F6C");
    c3.style.setProperty("padding","12px");
    c4.style.setProperty("text-align","center");
    c4.style.setProperty("border","1px solid #262F6C");
    c4.style.setProperty("padding","12px");
    var i1 = document.createElement("input");
    i1.setAttribute("type","text");
    i1.style.setProperty("outline","none");
    var i2 = document.createElement("input");
    i2.setAttribute("type","text");
    i2.style.setProperty("outline","none");
    var i3 = document.createElement("input");
    i3.setAttribute("type","text");
    i3.style.setProperty("outline","none");
    var i4 = document.createElement("input");
    i4.setAttribute("type","text");
    i4.style.setProperty("outline","none");
    var b1 = document.createElement("button");
    b1.setAttribute("type","button");
    b1.addEventListener('click', function handle(event) {
      b1.parentElement?.parentElement?.remove();
    });
    b1.addEventListener('mousedown', function handle(event) {
      b1.style.setProperty("background-color","rgb(38, 38, 159)");
    });
    b1.innerHTML = "Șterge premiu";
    b1.style.setProperty("width","fit-content");
    b1.style.setProperty("height","fit-content");
    b1.style.setProperty("outline","none");
    b1.style.setProperty("border","none");
    b1.style.setProperty("border-radius","3%");
    b1.style.setProperty("background-color","rgba(13,23,96,1)");
    b1.style.setProperty("padding","3px");
    b1.style.setProperty("font-family","Georgia, 'Times New Roman', Times, serif");
    b1.style.setProperty("font-size","15px");
    c1.appendChild(i1);
    c2.appendChild(i2);
    c3.appendChild(i3);
    c4.appendChild(b1);
    row.appendChild(c1);
    row.appendChild(c2);
    row.appendChild(c3);
    row.appendChild(c4);
    table?.appendChild(row);
  }

  deleteRow(event: any){
    event.target.parentElement.parentElement.remove();
  }

  checkImag(img: string): boolean{
    //console.log(img);
    return img == "" ? true : false;
  }

  checkLot(lot: string): boolean{
    let regexv = new RegExp("^[a-zA-Z0-9-.,'\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv.test(lot) && lot.length > 10;
  }

  checkPremii(premii: IPremiiLot[]): boolean{
    let regexv1 = new RegExp("^[a-zA-Z0-9-.,'&/:\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    let v1;
    let v2;
    let v3;
    let ok = 0;
    let size = premii.length;
    for(let i = 0; i < premii.length; i++)
    {
      v1 = regexv1.test(premii[i].denumirePremiu) && premii[i].denumirePremiu.length > 2;
      v2 = (premii[i].loc >= 0 && premii[i].loc < 200) || Number.isNaN(premii[i].loc) ? true : false;
      let anc = new Date().getFullYear();
      v3 = (premii[i].an > 1930 && premii[i].an < anc + 1) ? true : false;
      if(v1 && v2 && v3)
      {
        ok++;
      }
    }
    return ok == size ? true : false;
  }

  Adauga(imag: string, lot: string, info: string){
    if(this.afisCadeti && this.afisbtnAdauga)
    {
      let imag1;
      if(imag == "")
      {
        imag1 = "https://midlandshop.ro/images/thumbs/default-image_450.png";
      }
      else
      {
        imag1 = imag;
      }
      let lot1;
      let chklot;
      lot1 = lot;
      let info1 = info;
      let table = document.querySelector('#tbl-volei');
      let l = table?.children.length as number;
      this.prc = [];
      for(let i = 1; i < l; i++)
      {
        let a1 = table?.children[i].children[0].querySelector('input')?.value as string;
        let a2 = Number(table?.children[i].children[1].querySelector('input')?.value);
        let a3 = Number(table?.children[i].children[2].querySelector('input')?.value);
        this.prc.push({'id': 0, 'denumirePremiu': a1, 'loc': a2, 'an': a3});
      }
        this.cadet.id = 0;
        this.cadet.imagineLot = imag1;
        this.cadet.informatii = info1;
        this.cadet.lot = lot1 as string;
        this.cadet.premiiCadeti = this.prc;
        
        if(this.checkLot(this.cadet.lot) && (this.checkPremii(this.prc) || this.prc.length == 0 || l == 1))
        {
          this.getCadeti.addCadeti(this.cadet).subscribe();
          alert("Cadeții au fost adăugați cu succes.");
        }
        else
        {
          if(this.checkLot(this.cadet.lot) == false)
          {
            alert("Lotul este invalid. Lotul trebuie să conțină cel puțin 10 caractere.");
          }
          if(this.checkPremii(this.prc) == false)
          {
            alert("Premiile sunt invalide. Anul acordării premiului nu poate fi mai mare ca anul curent. Numele premiului trebuie să conțină cel puțin 3 caractere. Locul în campionat nu poate fi negativ.");
          }
        }
      
    }

    if(this.afisJuniori && this.afisbtnAdauga)
    {
      let imag1;
      if(imag == "")
      {
        imag1 = "https://midlandshop.ro/images/thumbs/default-image_450.png";
      }
      else
      {
        imag1 = imag;
      }
      let lot1;
      let chklot;
      lot1 = lot;
      let info1 = info;
      let table = document.querySelector('#tbl-volei');
      let l = table?.children.length as number;
      this.prj = [];
      for(let i = 1; i < l; i++)
      {
        let a1 = table?.children[i].children[0].querySelector('input')?.value as string;
        let a2 = Number(table?.children[i].children[1].querySelector('input')?.value);
        let a3 = Number(table?.children[i].children[2].querySelector('input')?.value);
        this.prj.push({'id': 0, 'denumirePremiu': a1, 'loc': a2, 'an': a3});
      }
        this.junior.id = 0;
        this.junior.imagineLot = imag1;
        this.junior.informatii = info1;
        this.junior.lot = lot1 as string;
        this.junior.premiiJuniori = this.prj;
        //console.log(this.junior);
        
        if(this.checkLot(this.junior.lot) && (this.checkPremii(this.prj) || this.prj.length == 0 || l == 1))
        {
          this.getJuniori.addJuniori(this.junior).subscribe();
          alert("Juniorii au fost adăugați cu succes.");
        }
        else
        {
          if(this.checkLot(this.junior.lot) == false)
          {
            alert("Lotul este invalid. Lotul trebuie să conțină cel puțin 10 caractere.");
          }
          if(this.checkPremii(this.prj) == false)
          {
            alert("Premiile sunt invalide. Anul acordării premiului nu poate fi mai mare ca anul curent. Numele premiului trebuie să conțină cel puțin 3 caractere. Locul în campionat nu poate fi negativ.");
          }
        }

    }

    if(this.afisMinivolei && this.afisbtnAdauga)
    {
      let imag1;
      if(imag == "")
      {
        imag1 = "https://midlandshop.ro/images/thumbs/default-image_450.png";
      }
      else
      {
        imag1 = imag;
      }
      let lot1;
      let chklot;
      lot1 = lot;
      let info1 = info;
      let table = document.querySelector('#tbl-volei');
      let l = table?.children.length as number;
      this.prm = [];
      for(let i = 1; i < l; i++)
      {
        let a1 = table?.children[i].children[0].querySelector('input')?.value as string;
        let a2 = Number(table?.children[i].children[1].querySelector('input')?.value);
        let a3 = Number(table?.children[i].children[2].querySelector('input')?.value);
        this.prm.push({'id': 0, 'denumirePremiu': a1, 'loc': a2, 'an': a3}); 
      }
        this.miniv.id = 0;
        this.miniv.imagineLot = imag1;
        this.miniv.informatii = info1;
        this.miniv.lot = lot1 as string;
        this.miniv.premiiMinivolei = this.prm;
        //console.log(this.miniv);
        
        if(this.checkLot(this.miniv.lot) && (this.checkPremii(this.prm) || this.prm.length == 0 || l == 1))
        {
          this.getMinivolei.addMinivolei(this.miniv).subscribe();
          alert("Lotul de mini volei a fost adăugat cu succes.");
        }
        else
        {
          if(this.checkLot(this.miniv.lot) == false)
          {
            alert("Lotul este invalid. Lotul trebuie să conțină cel puțin 10 caractere.");
          }
          if(this.checkPremii(this.prm) == false)
          {
            alert("Premiile sunt invalide. Anul acordării premiului nu poate fi mai mare ca anul curent. Numele premiului trebuie să conțină cel puțin 3 caractere. Locul în campionat nu poate fi negativ.");
          }
        }
  
    }

    if(this.afisSperante && this.afisbtnAdauga)
    {
      let imag1;
      if(imag == "")
      {
        imag1 = "https://midlandshop.ro/images/thumbs/default-image_450.png";
      }
      else
      {
        imag1 = imag;
      }
      let lot1;
      let chklot;
      lot1 = lot;
      let info1 = info;
      let table = document.querySelector('#tbl-volei');
      let l = table?.children.length as number;
      this.prs = [];
      for(let i = 1; i < l; i++)
      {
        let a1 = table?.children[i].children[0].querySelector('input')?.value as string;
        let a2 = Number(table?.children[i].children[1].querySelector('input')?.value);
        let a3 = Number(table?.children[i].children[2].querySelector('input')?.value);
        this.prs.push({'id': 0, 'denumirePremiu': a1, 'loc': a2, 'an': a3});
      }
        this.speranta.id = 0;
        this.speranta.imagineLot = imag1;
        this.speranta.informatii = info1;
        this.speranta.lot = lot1 as string;
        this.speranta.premiiSperante = this.prs;
        //console.log(this.speranta);
        
        if(this.checkLot(this.speranta.lot) && (this.checkPremii(this.prs) || this.prs.length == 0 || l == 1))
        {
          this.getSperante.addSperante(this.speranta).subscribe();
          alert("Lotul de speranțe a fost adăugat cu succes.");
        }
        else
        {
          if(this.checkLot(this.speranta.lot) == false)
          {
            alert("Lotul este invalid. Lotul trebuie să conțină cel puțin 10 caractere.");
          }
          if(this.checkPremii(this.prs) == false)
          {
            alert("Premiile sunt invalide. Anul acordării premiului nu poate fi mai mare ca anul curent. Numele premiului trebuie să conțină cel puțin 3 caractere. Locul în campionat nu poate fi negativ.");
          }
        }
      
    }
  }




}
