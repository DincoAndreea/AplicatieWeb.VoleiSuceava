import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IAntrenor } from 'src/app/model/antrenor';
import { IEchipa } from 'src/app/model/echipa';
import { IPaginare } from 'src/app/model/Paginare';
import { IPremiiP } from 'src/app/model/premii-persoane';
import { PersonalService } from '../../pagina-personal/service/PersonalService';

@Component({
  selector: 'app-antrenori',
  templateUrl: './antrenori.component.html',
  styleUrls: ['./antrenori.component.css']
})
export class AntrenoriComponent implements OnInit, OnDestroy {

  antrenori: IAntrenor[] = [];
  echipa: IEchipa[] = [];
  paginaActuala2: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  totalPagini2: number = 0;
  paginaActuala4: IPaginare = {
    numarElemente: 8,
    numarPagina: 0
  };
  totalPagini4: number = 0;
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
  afisPag2: boolean = true;
  afisPag4: boolean = false;
  valoare: string = ``;
  jucatorUpdate: IAntrenor = {
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

  constructor(private readonly getAntrenori: PersonalService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getAntrenori.getAntrenori().subscribe((nrPag2) => {
      this.totalPagini2=nrPag2;
      //console.dir("totalpagini1 = " + this.totalPagini2);
    });
    this.getAntrenori.getEchipe().subscribe((ech) => {
      this.echipa=JSON.parse(JSON.stringify(ech));
      //console.log(this.echipa);
    });
    this.PAntrenori();
  }

  PAntrenori(){
    this.lungimi = [];
    this.getAntrenori.getAntrenoriPag(this.paginaActuala2).subscribe((antrenori)=>{
      this.antrenori=JSON.parse(JSON.stringify(antrenori));
      for(let i = 0; i < this.antrenori.length; i++)
      {
        this.lungimi.push(Object.keys(this.antrenori[i].roluri).length);
        //console.log(this.lungimi[i]);
      }
    })
    //console.dir(this.antrenori);
  }

  PageAnt(event: Event, nrPag2:number){
    this.paginaActuala2.numarPagina=nrPag2;
    this.PAntrenori();
  }

  PageFiltruAnt(event: Event, nrPag4:number){
    this.paginaActuala4.numarPagina=nrPag4;
    this.AfisareAntrenoriFiltru();
  }

  AfisareAntrenoriFiltru(){
    this.getAntrenori.getAntrenoriFiltru(this.valoare,this.paginaActuala4).subscribe((antrenori)=>{
      this.antrenori=JSON.parse(JSON.stringify(antrenori["antrenori"]));
      //console.dir(antrenori);
      this.afisPag2=false;
      this.afisPag4=true;
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
    let dataN;
    //console.log(dataN1);
    this.premii = [];
    this.roluri.clear();
    const [d, m, a] = dataN1.split('/'); 
    let datac = new Date(+a,+m-1,+d);
    dataN = `${d}/${m}/${a}`;
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
      this.getAntrenori.updateAntrenor(this.jucatorUpdate).subscribe();
      alert("Antrenorul a fost modificat cu succes.");
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

  Sterge(j: IAntrenor){
    if(this.getAntrenori.deleteAntrenor(j).subscribe())
    {
      alert("Antrenorul a fost șters cu succes.");
    }
    else
    {
      alert("Nu s-a putut șterge antrenorul.");
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
