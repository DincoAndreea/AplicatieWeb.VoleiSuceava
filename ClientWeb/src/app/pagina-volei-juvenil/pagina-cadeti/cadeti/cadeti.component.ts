import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ICadeti } from 'src/app/model/cadeti';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { CadetiService } from '../service/CadetiService';

@Component({
  selector: 'app-cadeti',
  templateUrl: './cadeti.component.html',
  styleUrls: ['./cadeti.component.css']
})
export class CadetiComponent implements OnInit, OnDestroy {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  cadeti: ICadeti[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getCadeti: CadetiService) {
    this.unsubscribe$ = new Subject<void>();
    this.cadeti;
   }

  ngOnInit(): void {
    this.getCadeti.getCadeti().subscribe((cadeti) => {
      this.cadeti = JSON.parse(JSON.stringify(cadeti));
      localStorage.setItem("pagina-actuala","cadeti");
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
