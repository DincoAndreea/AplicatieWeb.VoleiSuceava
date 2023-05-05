import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ISeniori } from 'src/app/model/seniori';
import { AcasaService } from '../service/AcasaService';

@Component({
  selector: 'app-seniori',
  templateUrl: './seniori.component.html',
  styleUrls: ['./seniori.component.css']
})
export class SenioriComponent implements OnInit, OnDestroy {

  seniori: ISeniori = {
    'senioriDetalii' : ``,
    'senioriImagine' : ``,
    'senioriLot': ``,
    'senioriLista': []
  }

  unsubscribe$: Subject<void>;

  constructor(private readonly getAcasa: AcasaService) { 
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getSeniori();
    localStorage.setItem("pagina-actuala","seniori");
  }

  getSeniori(){
    this.getAcasa.getSeniori().subscribe((senior) => {
      this.seniori = JSON.parse(JSON.stringify(senior));
      //console.dir(this.seniori);
    })
  }


}
