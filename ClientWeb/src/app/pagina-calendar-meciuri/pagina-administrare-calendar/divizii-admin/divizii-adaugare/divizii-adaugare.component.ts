import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IDivizii } from 'src/app/model/divizii';
import { CalendarService } from 'src/app/pagina-calendar-meciuri/service/CalendarService';

@Component({
  selector: 'app-divizii-adaugare',
  templateUrl: './divizii-adaugare.component.html',
  styleUrls: ['./divizii-adaugare.component.css']
})
export class DiviziiAdaugareComponent implements OnInit, OnDestroy {

  divizieUpdate: IDivizii = {
    'id': 0,
    'nume': ""
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

  Adauga(nume: string){
    let nume1 = nume;
    this.divizieUpdate.nume = nume1;
    if(this.checkNume(this.divizieUpdate.nume))
    {
      this.getCalendar.addDivizii(this.divizieUpdate.nume).subscribe();
      alert("Divizia a fost adăugată cu succes.");
    }  
    else
    {
      if(this.checkNume(this.divizieUpdate.nume) == false)
      {
        alert("Numele diviziei este invalid. Numele diviziei trebuie să aibă cel puțin 3 caractere.");
      }
    }  
  }

  checkNume(nume: string): boolean{
    let regexv = new RegExp("^[a-zA-Z0-9-'&/,.:/\u0219\u0218\u021A\u021B\u0102\u0103\u00C2\u00E2\u00EE\u00CE\\s+]+$");
    return regexv.test(nume) && (nume.length > 2);
  }

}
