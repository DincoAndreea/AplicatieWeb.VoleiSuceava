import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IJuniori } from 'src/app/model/juniori';
import { IPremiiLot } from 'src/app/model/premii-lot';
import { JunioriService } from '../service/JunioriService';

@Component({
  selector: 'app-juniori',
  templateUrl: './juniori.component.html',
  styleUrls: ['./juniori.component.css']
})
export class JunioriComponent implements OnInit, OnDestroy {
  @Input() lot: string = ``;
  @Input() informatii: string = ``;
  @Input() imagine: string = ``;
  @Input() premii: IPremiiLot[] = [];

  juniori: IJuniori[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getJuniori: JunioriService) {
    this.unsubscribe$ = new Subject<void>();
    this.juniori;
   }

  ngOnInit(): void {
    this.getJuniori.getJuniori().subscribe((juniori) => {
      this.juniori = JSON.parse(JSON.stringify(juniori));
      localStorage.setItem("pagina-actuala","juniori");
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
}

}
