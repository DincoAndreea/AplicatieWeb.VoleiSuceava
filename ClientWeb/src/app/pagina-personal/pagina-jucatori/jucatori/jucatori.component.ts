import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IEchipa } from 'src/app/model/echipa';
import { IJucator } from 'src/app/model/jucator';
import { IPaginare } from 'src/app/model/Paginare';
import { IPremiiP } from 'src/app/model/premii-persoane';
import { PersonalService } from '../../pagina-personal/service/PersonalService';

@Component({
  selector: 'app-jucatori',
  templateUrl: './jucatori.component.html',
  styleUrls: ['./jucatori.component.css']
})
export class JucatoriComponent implements OnInit, OnDestroy {

  jucatori: IJucator[] = [];
  echipa: IEchipa[] = [];
  paginaActuala1: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  totalPagini1: number = 0;
  paginaActuala3: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  totalPagini3: number = 0;
  premii: IPremiiP[] = [];
  echipa1: IEchipa = {
    'csm': true,
    'id': 0,
    'logo': "",
    'nume': ""
  }
  roluri: Map<Date, string> = new Map();
  unsubscribe$: Subject<void>;
  lungimi: number[] = [];
  afisPag1: boolean = true;
  afisPag3: boolean = false;
  valoare: string = ``;
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

  constructor(private readonly getJucatori: PersonalService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getJucatori.getJucatori().subscribe((nrPag1) => {
      this.totalPagini1=nrPag1;
      //console.dir("totalpagini1 = " + this.totalPagini1);
    });
    this.getJucatori.getEchipeJucatori().subscribe((ech) => {
      this.echipa=JSON.parse(JSON.stringify(ech));
      //console.log(this.echipa);
    });
    this.PJucatori();
  }

  PJucatori(){
    this.lungimi = [];
    this.getJucatori.getJucatoriPag(this.paginaActuala1).subscribe((jucatori)=>{
      this.jucatori=JSON.parse(JSON.stringify(jucatori));
      for(let i = 0; i < this.jucatori.length; i++)
      {
        this.lungimi.push(Object.keys(this.jucatori[i].roluri).length);
        //console.log(this.lungimi[i]);
      }
    })
    //console.dir(this.jucatori);
  }

  PageJuc(event: Event, nrPag1:number){
    this.paginaActuala1.numarPagina=nrPag1;
    this.PJucatori();
  }

  PageFiltruJuc(event: Event, nrPag3:number){
    this.paginaActuala3.numarPagina=nrPag3;
    this.AfisareJucatoriFiltru();
  }

  AfisareJucatoriFiltru(){
    this.getJucatori.getJucatoriFiltru(this.valoare,this.paginaActuala3).subscribe((jucatori)=>{
      this.jucatori=JSON.parse(JSON.stringify(jucatori["jucatori"]));
      //console.dir(jucatori);
      this.afisPag1=false;
      this.afisPag3=true;
    })
  }

  AdaugaRol(event: any){
    let table = event.target.parentNode.previousSibling.firstChild.firstChild;
    //console.log(table);
    let tr = document.createElement("tr");
    let td1 = document.createElement("td");
    let td2 = document.createElement("td");
    let td3 = document.createElement("td");
    td1.style.setProperty("border","1px solid #0D1760");
    td1.style.setProperty("text-align","center");
    td2.style.setProperty("border","1px solid #0D1760");
    td2.style.setProperty("text-align","center");
    td3.style.setProperty("border","1px solid #0D1760");
    td3.style.setProperty("text-align","center");
    let date = document.createElement("input");
    let textarea = document.createElement("textarea");
    textarea.style.setProperty("outline","none");
    textarea.setAttribute("cols","30");
    textarea.setAttribute("rows","10");
    let button = document.createElement("input");
    date.setAttribute("type","date");
    button.setAttribute("type","button");
    button.setAttribute("value","Șterge rol");
    button.style.setProperty("padding","12px");
    button.style.setProperty("width","fit-content");
    button.style.setProperty("height","60px");
    button.style.setProperty("border-radius","0");
    button.style.setProperty("border","0");
    button.style.setProperty("background-color","#DFEBF3");
    button.style.setProperty("color","black");
    button.style.setProperty("font-family","Georgia, 'Times New Roman', Times, serif");
    button.style.setProperty("box-shadow","rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px");
    button.addEventListener('click', function handle(event) {
      button.parentElement?.parentElement?.remove();
    });
    button.addEventListener('mousedown', function handle(event) {
      button.style.setProperty("background-color","rgb(38, 38, 159)");
    });
    td1.appendChild(date);
    td2.appendChild(textarea);
    td3.appendChild(button);
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    table?.appendChild(tr);
  }

  AdaugaPremiu(event: any){
    let table = event.target.parentNode.previousSibling.firstChild.firstChild;
    //console.log(table);
    let tr = document.createElement("tr");
    let td1 = document.createElement("td");
    let td2 = document.createElement("td");
    let td3 = document.createElement("td");
    td1.style.setProperty("border","1px solid #0D1760");
    td1.style.setProperty("text-align","center");
    td2.style.setProperty("border","1px solid #0D1760");
    td2.style.setProperty("text-align","center");
    td3.style.setProperty("border","1px solid #0D1760");
    td3.style.setProperty("text-align","center");
    let date = document.createElement("input");
    let textarea = document.createElement("textarea");
    textarea.style.setProperty("outline","none");
    textarea.setAttribute("cols","30");
    textarea.setAttribute("rows","10");
    let button = document.createElement("input");
    date.setAttribute("type","date");
    button.setAttribute("type","button");
    button.setAttribute("value","Șterge premiu");
    button.style.setProperty("padding","12px");
    button.style.setProperty("width","fit-content");
    button.style.setProperty("height","60px");
    button.style.setProperty("border-radius","0");
    button.style.setProperty("border","0");
    button.style.setProperty("background-color","#DFEBF3");
    button.style.setProperty("color","black");
    button.style.setProperty("font-family","Georgia, 'Times New Roman', Times, serif");
    button.style.setProperty("box-shadow","rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px");
    button.addEventListener('click', function handle(event) {
      button.parentElement?.parentElement?.remove();
    });
    button.addEventListener('mousedown', function handle(event) {
      button.style.setProperty("background-color","rgb(38, 38, 159)");
    });
    td1.appendChild(date);
    td2.appendChild(textarea);
    td3.appendChild(button);
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    table?.appendChild(tr);
  }

  StergeRol(event: any){
    event.target?.parentElement?.parentElement.remove();
  }

  StergePremiu(event: any){
    event.target.parentElement.parentElement.remove();
  }

  Modifica(imagine1: string, nume1: string, prenume1: string, nationalitate1: string, dataN1: string, inaltime1: number, post1: string, descriere1: string, id: number, event: any, lot: number, imgh: string){
    let tablerol = event.target.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.firstChild.firstChild;
    let tablepre = event.target.parentNode.previousSibling.previousSibling.firstChild.firstChild;
    let imag;
    if(this.checkImag(imagine1) == false)
    {
      imag = imagine1;
    }
    else
    {
      imag = imgh;
    }
    this.premii = [];
    this.roluri.clear();
    let nume;
    let prenume;
    let natio;
    let dataN;
    let inaltime;
    let post;
    let descriere;
    const [d, m, a] = dataN1.split('/');
    let datac = new Date(+a,+m-1,+d);
    dataN = `${d}/${m}/${a}`;
    descriere = descriere1;
    let id1 = id;
    let l1 = tablepre?.children.length as number;
    if(l1 > 1)
    {
      for(let i = 1; i < l1; i++)
      {
        let a1 = tablepre!.children[i].children[0].querySelector('input')?.value;
        let a2 = tablepre!.children[i].children[1].querySelector('textarea')?.value;
        this.premii.push({'dataAcordare': a1, 'denumire': a2});
      }
    }
    
    
    let l2 = tablerol?.children.length as number;

    if(l2 > 1)
    {
      for(let j = 1; j < l2; j++)
      {
        let a3 = tablerol!.children[j].children[0].querySelector('input')?.value;
        let a4 = tablerol!.children[j].children[1].querySelector('textarea')?.value;
        this.roluri.set(a3,a4);
      }
    }
    
    //console.log(event.target.parentElement.parentElement.firstElementChild.children[1].firstElementChild.querySelector('select').options);
    //console.log("sel " + document.querySelector("#date")?.querySelector('select')?.options[1].selected);
    for(let k = 0; k < this.echipa.length; k++)
    {
      if(event.target.parentElement.parentElement.firstElementChild.children[1].firstElementChild.querySelector('select').options[k].selected)
      {
        this.jucatorUpdate.echipa = this.echipa[event.target.parentElement.parentElement.firstElementChild.children[1].firstElementChild.querySelector('select').options[k].index as number];
      }
    }
    const obj = Object.fromEntries(this.roluri);
    this.jucatorUpdate.id = id1;
    this.jucatorUpdate.nume = nume1 as string;
    this.jucatorUpdate.prenume = prenume1 as string;
    this.jucatorUpdate.dataNasterii = dataN as string;
    this.jucatorUpdate.linkPoza = imag;
    this.jucatorUpdate.nationalitate = nationalitate1 as string;
    this.jucatorUpdate.post = post1 as string;
    this.jucatorUpdate.inaltime = inaltime1 as number;
    this.jucatorUpdate.descriere = descriere1;
    this.jucatorUpdate.premii = this.premii;
    this.jucatorUpdate.roluri = obj;
    this.jucatorUpdate.lotSeniori = lot;
    //console.log(this.jucatorUpdate);

    if(this.checkNumePrenumeNatioPost(this.jucatorUpdate.nume) && this.checkNumePrenumeNatioPost(this.jucatorUpdate.prenume) && this.checkData(datac) && this.checkNumePrenumeNatioPost(this.jucatorUpdate.nationalitate) && this.checkNumePrenumeNatioPost(this.jucatorUpdate.post) && this.checkInaltime(this.jucatorUpdate.inaltime) && (this.checkPremii(this.premii) || this.premii.length == 0) && (this.checkRoluri(this.roluri) || Object.keys(this.roluri).length == 0))
    {
      this.getJucatori.updateJucator(this.jucatorUpdate).subscribe();
      alert("Jucătorul a fost modificat cu succes.");
    }
    else
    {
      if(this.checkNumePrenumeNatioPost(this.jucatorUpdate.nume) == false)
      {
        alert("Numele este incorect. Numele este obligatoriu și trebuie să aibă cel puțin 2 caractere.");
      }
      if(this.checkNumePrenumeNatioPost(this.jucatorUpdate.prenume) == false)
      {
        alert("Prenumele este incorect. Prenumele este obligatoriu și trebuie să aibă cel puțin 2 caractere.");
      }
      if(this.checkData(datac) == false)
      {
        alert("Data nașterii este incorectă.");
      }
      if(this.checkNumePrenumeNatioPost(this.jucatorUpdate.nationalitate) == false)
      {
        alert("Naționalitatea este invalidă. Naționalitatea este obligatorie și trebuie să aibă cel puțin 2 caractere.");
      }
      if(this.checkNumePrenumeNatioPost(this.jucatorUpdate.post) == false)
      {
        alert("Postul este invalid. Postul este obligatoriu și trebuie să aibă cel puțin 2 caractere.");
      }
      if(this.checkInaltime(this.jucatorUpdate.inaltime) == false)
      {
        alert("Înălțimea este invalidă.");
      }
      if(this.checkPremii(this.premii) == false)
      {
        alert("Premiile nu sunt valide.");
      }
      if(this.checkRoluri(this.roluri) == false)
      {
        alert("Rolurile nu sunt valide.");
      }
    }
  }

  Sterge(j: IJucator){
    if(this.getJucatori.deleteJucator(j).subscribe())
    {
      alert("Jucătorul a fost șters cu succes.");
    }
    else
    {
      alert("Nu s-a putut șterge jucătorul.");
    }
    
  }

  checkImag(img: string): boolean{
    //console.log(img);
    return img == "" ? true : false;
  }

  checkNumePrenumeNatioPost(str: string): boolean{
    let regexv = new RegExp("^[a-zA-Z-'\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv.test(str) && (str.length > 1);
  }

  checkInaltime(inaltime: number): boolean{
    return (inaltime > 120 && inaltime < 250) ? true : false;
  }

  checkData(data: Date): boolean{
    const data1 = new Date();
    const data3 = new Date('1930-01-01');
    const data2 = new Date(data); 
    return ((data2 > data3) && (data2 < data1)) ? true : false;
  }

  checkPremii(premii: IPremiiP[]): boolean{
    let regexv1 = new RegExp("^[a-zA-Z0-9-.',&/:\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    let size = premii.length;
    let ok = 0;
    for(let i = 0; i < premii.length; i++)
    {
      if(this.checkData(premii[i].dataAcordare) && premii[i].denumire.length > 2 && regexv1.test(premii[i].denumire))
      {
        ok++;
      }
    }
    return ok == size ? true : false;
  }

  checkRoluri(roluri: Map<Date, string>): boolean
  {
    let ok = 0;
    let size = Object.keys(roluri).length;
    roluri.forEach((value,key) =>
    {
      if(this.checkData(key) && value.length > 2)
      {
        ok++;
      }
    });
    return ok == size ? true : false;
  }

}
