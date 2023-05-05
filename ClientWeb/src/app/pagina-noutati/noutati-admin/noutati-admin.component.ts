import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { INoutateAdminFiltru } from 'src/app/model/noutate-admin-filtru';
import { INoutateAdminPreview } from 'src/app/model/noutate-admin-preview';
import { IPaginare } from 'src/app/model/Paginare';
import { NoutatiServices } from '../service/NoutatiService';

@Component({
  selector: 'app-noutati-admin',
  templateUrl: './noutati-admin.component.html',
  styleUrls: ['./noutati-admin.component.css']
})
export class NoutatiAdminComponent implements OnInit, OnDestroy {
  private tip_stire: number = 0;
  private tip_media: string = "i";
  private tip_stire_cronologic: number = 6;
  unsubscribe$: Subject<void>;
  noutati: INoutateAdminPreview[] = [];
  noutateFiltru: INoutateAdminFiltru = {
    numarElemente: 6,
    numarPagina: 0,
    dataStart: new Date(),
    dataSfarsit: new Date()
  }
  totalPagini: number = 0;
  currentRoute: string;

  constructor(private readonly noutatiServices: NoutatiServices, private router: Router) {
    this.unsubscribe$ = new Subject<void>;
    this.currentRoute = this.router.url;
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  getFiltrulData(): number {
    return this.tip_stire_cronologic;
  }

  filtrulStare(event: any) {
    //console.log("In filtrul 1 avem: "+event.target.value);
    this.tip_stire = event.target.value;
    this.goToPage(event, 0);
    this.populareStiri();
  }
  filtrulMedia(event: any) {
    //console.log("In filtrul 2 avem: "+event.target.value);
    this.tip_media = event.target.value;
    this.goToPage(event, 0);
    this.populareStiri();
    document.getElementsByClassName("selectedButton")[0].className="";
    event.srcElement.classList.add("selectedButton");
    // console.log(event.srcElement.classList);
  }
  filtrulData(event: any) {
    //console.log("In filtrul 3 avem: "+event.target.value);
    this.tip_stire_cronologic = event.target.value;
    this.goToPage(event, 0);
    this.populareStiri();
  }
  filtrulDataStart(event: any) {
    //console.log(event.target.value);
    this.noutateFiltru.dataStart = event.target.value;
    this.goToPage(event, 0);
    this.populareStiri();
  }
  filtrulDataStop(event: any) {
    //console.log(event.target.value);
    this.noutateFiltru.dataSfarsit = event.target.value;
    this.goToPage(event, 0);
    this.populareStiri();
  }

  goToPage(event: Event, nrPag: number) {
    this.noutateFiltru.numarPagina = nrPag;
    this.populareStiri();
    //console.dir(event);
  }

  populareStiri() {
      this.noutatiServices.getNoutatiFiltrate(this.noutateFiltru, this.tip_stire, this.tip_media, this.tip_stire_cronologic).subscribe((noutati) => {
        this.noutati = JSON.parse(JSON.stringify(noutati["stiri"]));
        this.totalPagini = JSON.parse(JSON.stringify(noutati.numarPagini));
        // console.dir(noutati);
      });
  }

  onButtonClick() {
    //console.log("Buton apasat");
    let rang = localStorage.getItem("utilizator");
    if (rang != null && rang != "v") {
      localStorage.setItem("noutateaDetaliata", "-1");
      //aici sa stochez ceva in local storage, ca sa stiu la onInit daca e creare de stire noua sau update si sa fac fetch
      this.router.navigate([`/noutati/noutateCreareEditare`]);
    }
  }

  accesareNoutate(event: Event, idStire: number) {
    let rang = localStorage.getItem("utilizator");
    if (rang != null && rang != "v") {
      localStorage.setItem("noutateaDetaliata", idStire.toString());
      //aici sa stochez ceva in local storage, ca sa stiu la onInit daca e creare de stire noua sau update si sa fac fetch
      this.router.navigate([`/noutati/noutateCreareEditare`]);
    }
  }
}
