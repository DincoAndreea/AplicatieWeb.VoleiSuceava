import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ISperante } from 'src/app/model/sperante';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { SperanteService } from '../service/SperanteService';

@Component({
  selector: 'app-sperante',
  templateUrl: './sperante.component.html',
  styleUrls: ['./sperante.component.css']
})
export class SperanteComponent implements OnInit, OnDestroy {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  sperante: ISperante[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getSperante: SperanteService) {
    this.unsubscribe$ = new Subject<void>();
    this.sperante;
   }

  ngOnInit(): void {
    this.getSperante.getSperante().subscribe((sperante) => {
      this.sperante = JSON.parse(JSON.stringify(sperante));
      localStorage.setItem("pagina-actuala","sperante");
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
}

}
