import { Component, Input, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IMinivolei } from 'src/app/model/minivolei';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { MinivoleiService } from '../service/MinivoleiService';

@Component({
  selector: 'app-mini-volei-comp',
  templateUrl: './mini-volei-comp.component.html',
  styleUrls: ['./mini-volei-comp.component.css']
})
export class MiniVoleiCompComponent implements OnInit {
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
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
}

}
