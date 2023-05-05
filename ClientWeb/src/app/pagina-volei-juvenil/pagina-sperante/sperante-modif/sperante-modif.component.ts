import { Component, Input, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { ISperante } from 'src/app/model/sperante';
import { SperanteService } from '../service/SperanteService';

@Component({
  selector: 'app-sperante-modif',
  templateUrl: './sperante-modif.component.html',
  styleUrls: ['./sperante-modif.component.css']
})
export class SperanteModifComponent implements OnInit {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  sperante: ISperante[] = [];
  speranteUpdate: ISperante = {
    'id': 0,
    'imagineLot': "",
    'informatii': "",
    'lot': "",
    'premiiSperante': []
  }
  premiiSpe: IPremiiLot[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getSperante: SperanteService) {
    this.unsubscribe$ = new Subject<void>();
   }

  ngOnInit(): void {
    this.getSperante.getSperante().subscribe((sperante) => {
      this.sperante = JSON.parse(JSON.stringify(sperante));
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  } 

  addRow(event: any): void {
    var table = event.target.parentElement.parentElement.firstChild.firstChild;
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
    var d1 = document.createElement("div");
    d1.style.setProperty("display","flex");
    d1.style.setProperty("justify-content","center");
    d1.style.setProperty("align-items","center");
    var b1 = document.createElement("button");
    b1.setAttribute("type","button");
    b1.addEventListener('click', function handle(event) {
      b1.parentElement?.parentElement?.parentElement?.remove();
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
    b1.style.setProperty("display","flex");
    b1.style.setProperty("justify-content","center");
    b1.style.setProperty("align-items","center");
    b1.style.setProperty("padding","20px");
    b1.style.setProperty("font-family","Georgia, 'Times New Roman', Times, serif");
    b1.style.setProperty("font-size","15px");
    d1.appendChild(b1);
    c1.appendChild(i1);
    c2.appendChild(i2);
    c3.appendChild(i3);
    c4.appendChild(d1);
    row.appendChild(c1);
    row.appendChild(c2);
    row.appendChild(c3);
    row.appendChild(c4);
    table?.appendChild(row);
  }

  deleteRow(event: any){
    event.target.parentElement.parentElement.parentElement.remove();
  }

  Modifica(lot: string, informatii: string, imagine: string, event: any, id: number, imagi: string){
    let lot1;
    let imag1;
    lot1 = lot;
    if(this.checkImag(imagine) == false)
    {
      imag1 = imagine;
    }
    else
    {
      imag1 = imagi;
    }
    let info1 = informatii;  
    let table = event.target.parentElement.previousSibling.children[0].children[0]; 
    //let table = document.querySelector("#body-spe")?.firstElementChild?.children[4].firstElementChild?.querySelector('table');
    //console.log("tab " + table);
    //let table = document.getElementById("premii")?.firstElementChild?.firstElementChild;
    //let table1 = document.getElementById("premii")?.firstElementChild?.firstElementChild?.children[1].children[0].querySelector('input')?.value;
    let id1 = id;
    //console.log(table);
    let l = table?.children.length as number;
    //console.log(l);
    this.premiiSpe = [];
    for(let i = 1; i < l; i++)
    {
      let a1 = table?.children[i].children[0].querySelector('input')?.value as string;
      let a2 = Number(table?.children[i].children[1].querySelector('input')?.value);
      let a3 = Number(table?.children[i].children[2].querySelector('input')?.value);
      this.premiiSpe.push({'id': id as number, 'denumirePremiu': a1, 'loc': a2, 'an': a3});
    }
    this.speranteUpdate!.id = id1 as number;
    this.speranteUpdate.imagineLot = imag1;
    this.speranteUpdate.informatii = info1;
    this.speranteUpdate.lot = lot1 as string;
    this.speranteUpdate.premiiSperante = this.premiiSpe;
    
    if(this.checkLot(this.speranteUpdate.lot) && (this.checkPremii(this.premiiSpe) || this.premiiSpe.length == 0))
    {
      this.getSperante.updateSperante(this.speranteUpdate).subscribe();
      alert("Lotul de speranțe a fost modificat cu succes.");
    }
    else
    {
      if(this.checkLot(this.speranteUpdate.lot) == false)
      {
        alert("Lotul este invalid. Lotul trebuie să conțină cel puțin 10 caractere.");
      }
      if(this.checkPremii(this.premiiSpe) == false)
      {
        alert("Premiile sunt invalide. Anul acordării premiului nu poate fi mai mare ca anul curent. Numele premiului trebuie să conțină cel puțin 3 caractere. Locul în campionat nu poate fi negativ.");
      }
    }
    
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

  Sterge(speranta: ISperante){
    if(this.getSperante.deleteSperante(speranta).subscribe())
    {
      alert("Lotul speranțe a fost șters cu succes.");
    }
    else
    {
      alert("Nu s-a putut șterge lotul speranțe.");
    }
    
  }

}
