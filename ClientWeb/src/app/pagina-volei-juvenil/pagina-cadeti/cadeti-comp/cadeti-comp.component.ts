import { Component, Input, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ICadeti } from 'src/app/model/cadeti';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { CadetiService } from '../service/CadetiService';

@Component({
  selector: 'app-cadeti-comp',
  templateUrl: './cadeti-comp.component.html',
  styleUrls: ['./cadeti-comp.component.css']
})
export class CadetiCompComponent implements OnInit {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  cadeti: ICadeti[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getCadeti: CadetiService) {
    this.unsubscribe$ = new Subject<void>();
   }

  ngOnInit(): void {
    this.getCadeti.getCadeti().subscribe((cadeti) => {
      this.cadeti = JSON.parse(JSON.stringify(cadeti));
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
