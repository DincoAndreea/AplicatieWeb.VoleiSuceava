import { Component, Input, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IJuniori } from 'src/app/model/juniori';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { JunioriService } from '../service/JunioriService';

@Component({
  selector: 'app-juniori-comp',
  templateUrl: './juniori-comp.component.html',
  styleUrls: ['./juniori-comp.component.css']
})
export class JunioriCompComponent implements OnInit {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  juniori: IJuniori[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getJuniori: JunioriService) {
    this.unsubscribe$ = new Subject<void>();
   }

  ngOnInit(): void {
    this.getJuniori.getJuniori().subscribe((juniori) => {
      this.juniori = JSON.parse(JSON.stringify(juniori));
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
}

}
