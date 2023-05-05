import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { IIstoric } from 'src/app/model/istoric';
import { IstoricService } from '../service/IstoricService';

@Component({
  selector: 'app-istoric',
  templateUrl: './istoric.component.html',
  styleUrls: ['./istoric.component.css']
})
export class IstoricComponent implements OnInit, OnDestroy {

  istoric: IIstoric = {
    'descriere': ``,
    'imagine': ``
  }
  unsubscribe$: Subject<void>;

  constructor(private readonly getIstoric: IstoricService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getIstoric.getIstoric().subscribe((istoric) => {
      this.istoric=JSON.parse(JSON.stringify(istoric));
      //console.log(this.istoric);
    })
    localStorage.setItem("pagina-actuala","istoric");
  }

}
