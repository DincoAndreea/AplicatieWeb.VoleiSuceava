import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IDivizii } from 'src/app/model/divizii';
import { CalendarService } from 'src/app/pagina-calendar-meciuri/service/CalendarService';

@Component({
  selector: 'app-divizii-modif',
  templateUrl: './divizii-modif.component.html',
  styleUrls: ['./divizii-modif.component.css']
})
export class DiviziiModifComponent implements OnInit, OnDestroy {

  divizieUpdate: IDivizii = {
    'id': 0,
    'nume': ""
  }
  divizii: IDivizii[] = [];
  unsubscribe$: Subject<void>;
  constructor(private readonly getCalendar:CalendarService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.afisareDivizii();
  }

  afisareDivizii() {
    this.getCalendar.getDivizii().subscribe((divizie) => {
      this.divizii = JSON.parse(JSON.stringify(divizie))
      //console.dir(this.divizii);
    })
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  Modifica(nume: string,id: number){
    let nume1 = nume;
    this.divizieUpdate.nume = nume1;
    this.divizieUpdate.id = id;
    if(this.checkNume(this.divizieUpdate.nume))
    {
      this.getCalendar.updateDivizii(this.divizieUpdate,id).subscribe();
      alert("Divizia a fost modificată cu succes.");
    }
    else
    {
      if(this.checkNume(this.divizieUpdate.nume) == false)
      {
        alert("Numele diviziei este invalid. Numele diviziei trebuie să aibă cel puțin 3 caractere.");
      }
    }
  }

  Sterge(divizie: IDivizii){
    if(this.getCalendar.deleteDivizii(divizie).subscribe())
    {
      alert("Divizia a fost ștearsă cu succes.");
    }
    else
    {
      alert("Nu s-a putut șterge divizia.");
    }   
  }

  checkNume(nume: string): boolean{
    let regexv = new RegExp("^[a-zA-Z0-9-'&/,.:/\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv.test(nume) && (nume.length > 2);
  }

}
