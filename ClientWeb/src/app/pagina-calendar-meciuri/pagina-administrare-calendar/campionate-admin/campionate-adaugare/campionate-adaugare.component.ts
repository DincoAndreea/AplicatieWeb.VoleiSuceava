import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ICampionat } from 'src/app/model/campionat';
import { ICampionatDTO } from 'src/app/model/campionatDTO';
import { IJudet } from 'src/app/model/judet';
import { ILocalitate } from 'src/app/model/localitate';
import { ITara } from 'src/app/model/tara';
import { CalendarService } from 'src/app/pagina-calendar-meciuri/service/CalendarService';

@Component({
  selector: 'app-campionate-adaugare',
  templateUrl: './campionate-adaugare.component.html',
  styleUrls: ['./campionate-adaugare.component.css']
})
export class CampionateAdaugareComponent implements OnInit, OnDestroy {

  locatii: ILocalitate[] = [];
  tara: ITara = {
    'id': 0,
    'tara': ""
  }
  judet: IJudet = {
    'id': 0,
    'judet': "",
    'tara': this.tara
  }
  localitate: ILocalitate = {
    'id': 0,
    'judet': this.judet,
    'localitate': ""
  }
  /*campionatUpdate: ICampionat = {
    'id': 0,
    'denumire': "",
    'dataIncepere': new Date(),
    'dataSfarsit': new Date(),
    'localitate': this.localitate 
  }*/
  campionatUpdate: ICampionatDTO = {
    'id': 0,
    'denumire': "",
    'dataIncepere': new Date(),
    'dataSfarsit': new Date(),
    'localitate': "",
    'judet': "",
    'tara': "" 
  }
  unsubscribe$: Subject<void>;
  constructor(private readonly getCalendar:CalendarService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.getCalendar.getLocatii().subscribe((locatii) => {
      this.locatii=JSON.parse(JSON.stringify(locatii));
    })
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  Adauga(nume: string, tara: string, judet: string, localitate: string){
    let date11 = document.querySelector('#data-inceput-campionat-adaugare-div')?.querySelector('input')?.valueAsDate;
    let date12 = document.querySelector('#data-sfarsit-campionat-adaugare-div')?.querySelector('input')?.valueAsDate;
    this.campionatUpdate.denumire = nume;
    this.campionatUpdate!.dataIncepere = date11 as Date;
    this.campionatUpdate!.dataSfarsit = date12 as Date;  
    if(tara != "" && judet != "" && localitate != "")
    {
      this.campionatUpdate.localitate = localitate;
      this.campionatUpdate.tara = tara;
      this.campionatUpdate.judet = judet;
    }
    else
    {
      if(document.querySelector("#localitate-div-campionat-adaugare-select")?.querySelector('select')?.options[this.locatii.length].selected)
      {
        this.campionatUpdate.localitate = "";
        this.campionatUpdate.tara = "";
        this.campionatUpdate.judet = "";
      }
      else
      {
        for(let k = 0; k < this.locatii.length; k++)
        {
          if(document.querySelector("#localitate-div-campionat-adaugare-select")?.querySelector('select')?.options[k].selected)
          {
            this.localitate = this.locatii[document.querySelector("#localitate-div-campionat-adaugare-select")?.querySelector('select')?.options[k].index as number];
          }
        }
        this.campionatUpdate.localitate = this.localitate.localitate;
        this.campionatUpdate.tara = this.localitate.judet.tara.tara;
        this.campionatUpdate.judet = this.localitate.judet.judet;
      }
    }
    //console.log(this.campionatUpdate);
    if(this.campionatUpdate.dataIncepere == null)
    {
      this.campionatUpdate.dataIncepere = new Date('1920-01-01');
    }
    if(this.campionatUpdate.dataSfarsit == null)
    {
      this.campionatUpdate.dataSfarsit = new Date('1920-01-01');
    }
    if(this.checkCampionat(this.campionatUpdate))
    {
      this.getCalendar.addCampionate(this.campionatUpdate).subscribe();
      alert("Campionatul a fost adăugat cu succes.");
    }   
    else
    {
      if(this.checkDate(this.campionatUpdate.dataIncepere) == false)
      {
        alert("Data început este invalidă.");
      }
      if(this.checkDate(this.campionatUpdate.dataSfarsit) == false)
      {
        alert("Data sfârșit este invalidă.");
      }
      if(this.checkNume(this.campionatUpdate.denumire) == false)
      {
        alert("Numele campionatului este invalid. Numele trebuie să conțină cel puțin 3 caractere.");
      }
      if(this.checkDate2(this.campionatUpdate.dataSfarsit,this.campionatUpdate.dataIncepere) == false)
      {
        alert("Data de început nu poate fi mai mare ca data de sfârșit.");
      }
      if(this.checkLocatie(this.campionatUpdate.tara,this.campionatUpdate.judet,this.campionatUpdate.localitate) == false && this.campionatUpdate.tara.length > 0 && this.campionatUpdate.judet.length > 0 && this.campionatUpdate.localitate.length > 0)
      {
        alert("Locația este invalidă. Numele țării, județului și localității trebuie să aibă cel puțin 3 caractere.");
      }
    }
  }

  checkDate(date: Date): boolean{
    let d1 = new Date(date).getFullYear();
    return d1 > 1930 && d1 < (d1 + 2);
  }

  checkDate2(date1: Date, date2: Date): boolean{
    return date1 > date2;
  }

  checkNume(nume: string): boolean{
    let regexv = new RegExp("^[a-zA-Z0-9-'&/.,:\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv.test(nume) && (nume.length > 2);
  }

  checkLocatie(tara: string, judet: string, localitate: string): boolean{
    let regexv1 = new RegExp("^[a-zA-Z-',\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$"); 
    let regexv = new RegExp("^[a-zA-Z0-9-',\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv1.test(tara) && regexv.test(judet) && regexv.test(localitate) && (tara.length > 2) && (judet.length > 2) && (localitate.length > 2);
  }

  checkCampionat(campionat: ICampionatDTO): boolean
  {
    return this.checkNume(campionat.denumire) && this.checkDate(campionat.dataIncepere) && this.checkDate(campionat.dataSfarsit) && this.checkDate2(campionat.dataSfarsit,campionat.dataIncepere) && this.checkLocatie(campionat.tara,campionat.judet,campionat.localitate);
  }

}
