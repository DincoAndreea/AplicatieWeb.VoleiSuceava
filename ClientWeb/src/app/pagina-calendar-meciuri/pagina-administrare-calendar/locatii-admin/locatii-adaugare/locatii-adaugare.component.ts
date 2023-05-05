import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IJudet } from 'src/app/model/judet';
import { ILocalitate } from 'src/app/model/localitate';
import { ILocalitati } from 'src/app/model/localitate-adaugare';
import { ITara } from 'src/app/model/tara';
import { CalendarService } from 'src/app/pagina-calendar-meciuri/service/CalendarService';

@Component({
  selector: 'app-locatii-adaugare',
  templateUrl: './locatii-adaugare.component.html',
  styleUrls: ['./locatii-adaugare.component.css']
})
export class LocatiiAdaugareComponent implements OnInit, OnDestroy {

  
  locatieUpdate: ILocalitati = {
    'localitate': "",
    'judet': "",
    'tara': ""
  }
  unsubscribe$: Subject<void>;
  constructor(private readonly getCalendar:CalendarService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  Adauga(tara: string, judet: string, localitate: string){
    this.locatieUpdate.tara = tara;
    this.locatieUpdate.judet = judet;
    this.locatieUpdate.localitate = localitate;
    if(this.checkLocatie(this.locatieUpdate.tara, this.locatieUpdate.judet, this.locatieUpdate.localitate))
    {
      this.getCalendar.addLocatii(this.locatieUpdate).subscribe();
      alert("Locația a fost adăugată cu succes.");
    }   
    else
    {
      if(this.checkLocatie(this.locatieUpdate.tara, this.locatieUpdate.judet, this.locatieUpdate.localitate) == false)
      {
        alert("Locația este invalidă. Numele țării, județului și localității trebuie să aibă cel puțin 3 caractere.");
      }
    } 
  }

  checkLocatie(tara: string, judet: string, localitate: string): boolean{
    let regexv1 = new RegExp("^[a-zA-Z-',.\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$"); 
    let regexv = new RegExp("^[a-zA-Z0-9-',.\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv1.test(tara) && regexv.test(judet) && regexv.test(localitate) && (tara.length > 2) && (judet.length > 2) && (localitate.length > 2);
  }

}
