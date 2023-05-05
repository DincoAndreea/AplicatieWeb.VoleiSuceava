import { Component, Input, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { ISperante } from 'src/app/model/sperante';
import { SperanteService } from '../service/SperanteService';

@Component({
  selector: 'app-sperante-comp',
  templateUrl: './sperante-comp.component.html',
  styleUrls: ['./sperante-comp.component.css']
})
export class SperanteCompComponent implements OnInit {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  sperante: ISperante[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getSperante: SperanteService) {
    this.unsubscribe$ = new Subject<void>();
   }

  ngOnInit(): void {
    this.getSperante.getSperante().subscribe((sperante) => {
      this.sperante = JSON.parse(JSON.stringify(sperante));
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
}

}
