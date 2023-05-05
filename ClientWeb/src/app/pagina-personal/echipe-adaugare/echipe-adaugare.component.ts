import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IEchipa } from 'src/app/model/echipa';
import { PersonalService } from '../pagina-personal/service/PersonalService';

@Component({
  selector: 'app-echipe-adaugare',
  templateUrl: './echipe-adaugare.component.html',
  styleUrls: ['./echipe-adaugare.component.css']
})
export class EchipeAdaugareComponent implements OnInit, OnDestroy {
  unsubscribe$: Subject<void>;

  echipaUpdate: IEchipa = {
    'id': 0,
    'nume': "",
    'logo': "",
    'csm': false
  }
  
  constructor(private readonly getPersonal: PersonalService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
  }

  Adauga(imag: string, nume: string, stare: boolean){
    let imagi;
    let n;
    if(this.checkImag(imag) == false)
    {
      imagi = imag;
    }
    else
    {
      imagi = "https://midlandshop.ro/images/thumbs/default-image_450.png";
    }
    n = nume;
    this.echipaUpdate.csm = stare;
    this.echipaUpdate.logo = imagi;
    this.echipaUpdate.nume = n as string;
    if(this.checkNume(this.echipaUpdate.nume))
    {
      this.getPersonal.addEchipa(this.echipaUpdate).subscribe();
      alert("Echipa a fost adăugată cu succes.");
    }
    else
    {
      if(this.checkNume(this.echipaUpdate.nume) == false)
      {
        alert("Numele introdus este incorect. Numele trebuie să aibă cel puțin 3 caractere și poate conține doar caracterele speciale -, ', &, / și .(punct) .");
      }
      
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
