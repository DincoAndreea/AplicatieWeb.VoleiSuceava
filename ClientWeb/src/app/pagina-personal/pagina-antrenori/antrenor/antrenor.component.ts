import { Component, Input, OnInit } from '@angular/core';
import { IPremiiP } from 'src/app/model/premii-persoane';

@Component({
  selector: 'app-antrenor',
  templateUrl: './antrenor.component.html',
  styleUrls: ['./antrenor.component.css']
})
export class AntrenorComponent implements OnInit {
  @Input() nume: string = ``;
  @Input() prenume: string = ``;
  @Input() dataNasterii: string = ``;
  @Input() inaltime: number = 0;
  @Input() post: string = ``;
  @Input() descriere: string = ``;
  @Input() nationalitate: string = ``;
  @Input() linkPoza: string = ``;
  @Input() premii: IPremiiP[] = [];
  @Input() roluri!: Map<Date, string>;
  afis: boolean = true;

  constructor() { }

  ngOnInit(): void {
    if(Object.keys(this.roluri).length > 0)
    {
      this.afis = true;
    }
    else
    {
      this.afis = false;
    }
  }

}
