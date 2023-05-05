import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IMinivolei } from 'src/app/model/minivolei';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { MinivoleiService } from '../service/MinivoleiService';

@Component({
  selector: 'app-mini-volei',
  templateUrl: './mini-volei.component.html',
  styleUrls: ['./mini-volei.component.css']
})
export class MiniVoleiComponent implements OnInit, OnDestroy {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  minivolei: IMinivolei[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getMinivolei: MinivoleiService) {
    this.unsubscribe$ = new Subject<void>();
    this.minivolei;
   }

  ngOnInit(): void {
    this.getMinivolei.getMinivolei().subscribe((minivolei) => {
      this.minivolei = JSON.parse(JSON.stringify(minivolei));
      localStorage.setItem("pagina-actuala","minivolei");
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
}

}
