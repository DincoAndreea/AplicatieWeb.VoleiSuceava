import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { IEchipa } from 'src/app/model/echipa';
import { ILocatie } from 'src/app/model/locatie';
import { IMeciC } from 'src/app/model/meci-complet';
import { IMeciDetaliat } from 'src/app/model/meci-detaliat';
import { CalendarService } from '../service/CalendarService';

@Component({
  selector: 'app-calendar-meciuri-detaliat',
  templateUrl: './calendar-meciuri-detaliat.component.html',
  styleUrls: ['./calendar-meciuri-detaliat.component.css']
})
export class CalendarMeciuriDetaliatComponent implements OnInit, OnDestroy {
  unsubscribe$: Subject<void>;
  locatie:ILocatie={'id':0,'judet':{'id':0,'judet':'','tara':{'id':0,'tara':''}},'localitate':''}
  meciDetaliat: IMeciDetaliat = {
    'id': 0,
    'scor': [0,0],
    'link': "",
    'divizie': {'id':0,'nume':""},
    'campionat': {'id':0,'denumire':'','dataIncepere':new Date(),'dataSfarsit':new Date(), 'localitate':this.locatie},
    'echipe': [{'id':0,'nume':'','logo':'','csm':false},{'id':0,'nume':'','logo':'','csm':false}],
    'locatie': this.locatie,
    'dataMeci':new Date()
  }
  stringData:string="";
//ce json am nevoie
    // 'id': 0,
    // 'scor': [0,0],
    // 'link': "",
    // 'campionat': {'id':0,'denumire':''},
    // 'echipe': [{'id':0,'nume':'','logo':''},{'id':0,'nume':'','logo':''}],
    // 'locatie': {'id':0,'localitate':'','judet':'','tara':''},
    // 'dataMeci':new Date()
//

  
  constructor(private readonly CalendarMeciuri: CalendarService, private router: Router) { 
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.CalendarMeciuri.getMeci(this.CalendarMeciuri.retrieveIdMeci()).subscribe((meci)=>{
      this.meciDetaliat=JSON.parse(JSON.stringify(meci));
      //(this.meciDetaliat);
      this.stringData+="Data: "
      let aux = new Date(this.meciDetaliat.dataMeci);
      this.stringData+=aux.getDate().toString()+"/";
      this.stringData+=((aux.getMonth()+1)<10?"0"+(aux.getMonth()+1).toString():(aux.getMonth()+1).toString()).toString()+"/";//interpretabil daca mai trebuie +1
      this.stringData+=aux.getFullYear().toString()+" Ora: ";
      this.stringData+=(aux.getHours()<10?"0"+aux.getHours().toString():aux.getHours().toString()).toString()+":";
      this.stringData+=(aux.getMinutes()<10?"0"+aux.getMinutes().toString():aux.getMinutes().toString()).toString();
      //console.log("Data: "+this.stringData);
    })
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
