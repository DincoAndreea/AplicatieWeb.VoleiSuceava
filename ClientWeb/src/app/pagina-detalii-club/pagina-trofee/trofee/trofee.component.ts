import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ICampionat } from 'src/app/model/campionat';
import { ITrofee } from 'src/app/model/trofee';
import { ITrofeePag } from 'src/app/model/trofee-pagina';
import { TrofeeService } from '../service/TrofeeService';

@Component({
  selector: 'app-trofee',
  templateUrl: './trofee.component.html',
  styleUrls: ['./trofee.component.css']
})
export class TrofeeComponent implements OnInit, OnDestroy {
  trofee: ITrofee[] = [];
  trof: ITrofeePag = {
    'descriere': ``,
    'imagine': ``
  }

  unsubscribe$: Subject<void>;

  constructor(private readonly getTrofee:TrofeeService) {
    this.unsubscribe$ = new Subject<void>();
   }

  ngOnInit(): void {
    this.afisareTrofee();
    this.getTrofee.getTrofeePag().subscribe((trofee) => {
      this.trof=JSON.parse(JSON.stringify(trofee));
      //console.dir(this.trof);
    })
    localStorage.setItem("pagina-actuala","trofee");
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  afisareTrofee(){
    this.getTrofee.getTrofee().subscribe((trofee)=>{
      this.trofee=JSON.parse(JSON.stringify(trofee))
      //console.log(this.trofee);
    })
  }

}
