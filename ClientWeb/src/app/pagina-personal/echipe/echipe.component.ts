import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IEchipa } from 'src/app/model/echipa';
import { PersonalService } from '../pagina-personal/service/PersonalService';

@Component({
  selector: 'app-echipe',
  templateUrl: './echipe.component.html',
  styleUrls: ['./echipe.component.css']
})
export class EchipeComponent implements OnInit, OnDestroy {
  echipa: IEchipa[] = [];
  echipaUpdate: IEchipa = {
    'id': 0,
    'nume': "",
    'logo': "",
    'csm': false
  }
  unsubscribe$: Subject<void>;

  constructor(private readonly getPersonal: PersonalService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getPersonal.getEchipe().subscribe((ech) => {
      this.echipa=JSON.parse(JSON.stringify(ech));
      //console.log(this.echipa);
    })
  }

  Modifica(imagine: string, nume: string, stare: boolean, id: number, img: string)
  {
    let imag;
    let n;
    if(this.checkImag(imagine) == false)
    {
      imag = imagine;
    }
    else
    {
      imag = img;
    }
    n = nume;
    this.echipaUpdate.csm = stare;
    this.echipaUpdate.id = id;
    this.echipaUpdate.logo = imag;
    this.echipaUpdate.nume = n as string;
    if(this.checkNume(this.echipaUpdate.nume))
    {
      this.getPersonal.updateEchipa(this.echipaUpdate).subscribe();
      alert("Echipa a fost modificată cu succes.");
    }
    else
    {
      if(this.checkNume(this.echipaUpdate.nume) == false)
      {
        alert("Numele introdus este incorect. Numele trebuie să aibă cel puțin 3 caractere și poate conține doar caracterele speciale -, ', &, / și .(punct) .");
      }
    }
  }

  Sterge(e: IEchipa)
  {
    if(this.getPersonal.deleteEchipa(e).subscribe())
    {
      alert("Echipa a fost ștearsă cu succes.");
    }
    else
    {
      alert("Nu s-a putut șterge echipa.");
    }
    
  }

  checkImag(img: string): boolean{
    //console.log(img);
    return img == "" ? true : false;
  }

  checkNume(str: string): boolean{
    let regexv = new RegExp("^[a-zA-Z0-9-',&/.*)(\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv.test(str) && str.length > 2;
  }

}
