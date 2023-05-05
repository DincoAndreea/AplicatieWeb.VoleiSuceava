import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { ICreatorDeContinut } from 'src/app/model/creatorDeContinut';
import { AdministrareConturiModule } from '../feature/AdministrareConturiModule';
import { AdministrareConturiService } from '../service/AdministrareConturiService';

@Component({
  selector: 'app-pagina-administrare-conturi',
  templateUrl: './pagina-administrare-conturi.component.html',
  styleUrls: ['./pagina-administrare-conturi.component.css']
})
export class PaginaAdministrareConturiComponent implements OnInit, OnDestroy {
  unsubscribe$: Subject<void>;
  creatori: ICreatorDeContinut[] = [];


  adaugaForm = new FormGroup({
    nume: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(40),
    ]),
    numeUtilizator: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(40),
    ]),
    parolaUtilizator: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
      Validators.maxLength(40),
    ]),
  });

  constructor(private readonly administrare: AdministrareConturiService) {
    this.unsubscribe$ = new Subject<void>();
  }
  ngOnInit(): void {
    this.populareFront();
  }
  adauga() {
    if (this.adaugaForm.valid) {
      let aux: ICreatorDeContinut = {
        'id': -1,
        'nume': '',
        'numeUtilizator': '',
        'parola': '',
      }
      aux.nume = (<HTMLInputElement>document.getElementById("numeAdd")).value;
      aux.numeUtilizator = (<HTMLInputElement>document.getElementById("numeUtilizatorAdd")).value;
      aux.parola = (<HTMLInputElement>document.getElementById("parolaAdd")).value;
      //console.log(aux);
      this.administrare.addCreator(aux).subscribe((creator) => {
        this.populareFront();
        (<HTMLInputElement>document.getElementById("numeAdd")).value = "";
        (<HTMLInputElement>document.getElementById("numeUtilizatorAdd")).value = "";
        (<HTMLInputElement>document.getElementById("parolaAdd")).value = "";
        this.coloreazaFormAlb();
      });
      this.adaugaForm.setValue({
        'nume': '',
        'numeUtilizator': '',
        'parolaUtilizator': ''
      });
    }
    else {
      this.coloreazaFormRosu();
    }
  }
  sterge(id: number) {
    this.administrare.deleteCreator(id).subscribe((creator) => {
      this.populareFront();
    });
  }
  populareFront() {
    this.administrare.getCreatori().subscribe((creator) => {
      this.creatori = JSON.parse(JSON.stringify(creator));
      //console.log(this.creatori);
    });
  }
  coloreazaFormRosu() {
    if (this.adaugaForm.controls.nume.invalid) {
      (<HTMLInputElement>document.getElementById("numeAdd")).className = "ingustRosu";
    }
    else {
      (<HTMLInputElement>document.getElementById("numeAdd")).className = "ingust";
    }
    if (this.adaugaForm.controls.numeUtilizator.invalid) {
      (<HTMLInputElement>document.getElementById("numeUtilizatorAdd")).className = "ingustRosu";
    }
    else {
      (<HTMLInputElement>document.getElementById("numeUtilizatorAdd")).className = "ingust";
    }
    if (this.adaugaForm.controls.parolaUtilizator.invalid) {
      (<HTMLInputElement>document.getElementById("parolaAdd")).className = "ingustRosu";
    }
    else {
      (<HTMLInputElement>document.getElementById("parolaAdd")).className = "ingust";
    }
  }
  coloreazaFormAlb() {
    (<HTMLInputElement>document.getElementById("numeAdd")).className = "ingust";
    (<HTMLInputElement>document.getElementById("numeUtilizatorAdd")).className = "ingust";
    (<HTMLInputElement>document.getElementById("parolaAdd")).className = "ingust";
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
