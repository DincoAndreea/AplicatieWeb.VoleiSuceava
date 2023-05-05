import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IViziune } from 'src/app/model/viziune';
import { ViziuneService } from '../service/ViziuneService';

@Component({
  selector: 'app-viziune',
  templateUrl: './viziune.component.html',
  styleUrls: ['./viziune.component.css']
})
export class ViziuneComponent implements OnInit, OnDestroy {
  viziune: IViziune = {
    'descriere': ``,
    'imagine': ``
  }

  unsubscribe$: Subject<void>;

  constructor(private readonly getViziune: ViziuneService) { 
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getViziune.getViziune().subscribe((viziune) => {
      this.viziune=JSON.parse(JSON.stringify(viziune));
      //console.dir(this.viziune);
    })
    localStorage.setItem("pagina-actuala","viziune");
  }

}
