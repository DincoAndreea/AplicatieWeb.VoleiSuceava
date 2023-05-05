import { Component, OnInit } from '@angular/core';
import { isEmpty } from 'rxjs';
import { INoutateDetaliat } from 'src/app/model/noutate-detaliat';
import { NoutatiServices } from '../service/NoutatiService';

@Component({
  selector: 'app-noutate-detaliat',
  templateUrl: './noutate-detaliat.component.html',
  styleUrls: ['./noutate-detaliat.component.css']
})
export class NoutateDetaliatComponent implements OnInit {
  noutate: INoutateDetaliat = {
    'id': 0,
    'titlu': "",
    'descriere': "",
    'hashtaguri': "",
    'imagini': "",
    'videoclipuri': "",
    'dataPostare': new Date(),
    'draft': false
  };


  constructor(private readonly noutatiServices: NoutatiServices) {

  }

  ngOnInit(): void {
    let idStire: string = localStorage.getItem("noutateaDetaliata") || "";
    this.noutatiServices.getNoutateDetaliat(Number.parseInt(idStire)).subscribe((noutate) => {
      this.noutate = JSON.parse(JSON.stringify(noutate));
      //console.dir(this.noutate);
      localStorage.setItem("pagina-actuala", "noutate-detaliat");
    });
    setTimeout(() =>{this.oneShotEvent()},250);
  };

  oneShotEvent() {
    let el = document.getElementsByTagName("img");
    for (var i = 0; i < el.length; i++) {
      el[i].addEventListener("click", function (e) {
        if ((<HTMLTextAreaElement>e.target).className.length < 5) {
          (<HTMLTextAreaElement>e.target).className = "zoom transformDinamic";
        }
        else {
          (<HTMLTextAreaElement>e.target).className = "zoom";
        }
        //console.log("The clicked one is "+ (<HTMLTextAreaElement>e.target).className);
        //console.dir(e);
      });
    }
  }
}