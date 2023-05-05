import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IJudet } from 'src/app/model/judet';
import { ILocalitate } from 'src/app/model/localitate';
import { ILocalitati } from 'src/app/model/localitate-adaugare';
import { ITara } from 'src/app/model/tara';
import { CalendarService } from 'src/app/pagina-calendar-meciuri/service/CalendarService';

@Component({
  selector: 'app-locatii-modif',
  templateUrl: './locatii-modif.component.html',
  styleUrls: ['./locatii-modif.component.css']
})
export class LocatiiModifComponent implements OnInit, OnDestroy {

  /*tara: ITara = {
    'id': 0,
    'tara': ""
  }
  judet: IJudet = {
    'id': 0,
    'judet': "",
    'tara': this.tara
  }
  locatieUpdate: ILocalitate = {
    'id': 0,
    'judet': this.judet,
    'localitate': ""
  }*/
  locatieUpdate: ILocalitati = {
    'localitate': "",
    'judet': "",
    'tara': ""
  }
  locatii: ILocalitate[] = [];
  unsubscribe$: Subject<void>;
  constructor(private readonly getCalendar:CalendarService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.afisareLocatii();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  afisareLocatii(){
    this.getCalendar.getLocatii().subscribe((locatii) => {
      this.locatii=JSON.parse(JSON.stringify(locatii));
      //console.dir(this.locatii);
    })
  }

  Modifica(tara: string, judet: string, localitate: string, id: number){
    this.locatieUpdate.tara = tara;
    this.locatieUpdate.judet = judet;
    this.locatieUpdate.localitate = localitate;
    //console.log(this.locatieUpdate);
    if(this.checkLocatie(this.locatieUpdate.tara, this.locatieUpdate.judet, this.locatieUpdate.localitate))
    {
      this.getCalendar.updateLocatii(this.locatieUpdate, id).subscribe();
      alert("Locația a fost modificată cu succes.");
    }
    else
    {
      if(this.checkLocatie(this.locatieUpdate.tara, this.locatieUpdate.judet, this.locatieUpdate.localitate) == false)
      {
        alert("Locația este invalidă. Numele țării, județului și localității trebuie să aibă cel puțin 3 caractere.");
      }
    }
  }

  Sterge(locatie: ILocalitate){
    if(this.getCalendar.deleteLocatii(locatie).subscribe())
    {
      alert("Locația a fost ștearsă cu succes.");
    }
    else
    {
      alert("Nu s-a putut șterge locația");
    }
  }

  checkLocatie(tara: string, judet: string, localitate: string): boolean{
    let regexv1 = new RegExp("^[a-zA-Z-',.\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$"); 
    let regexv = new RegExp("^[a-zA-Z0-9-',.\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv1.test(tara) && regexv.test(judet) && regexv.test(localitate) && (tara.length > 2) && (judet.length > 2) && (localitate.length > 2);
  }

}
