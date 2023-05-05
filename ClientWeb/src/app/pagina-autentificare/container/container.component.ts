import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Utilizator } from 'src/app/model/utilizator';
import { AutentificareService } from '../service/AutentificareService';

@Component({
  selector: 'app-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.css'],
})
export class ContainerComponent implements OnInit {
  utilizator: Utilizator | undefined;
  autentificareForm = new FormGroup({
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

  private gasit: boolean = false;

  mesajFeedback: string = '';
  tipUtilizator:string = localStorage.getItem('utilizator')||"v";
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private autentificareService: AutentificareService
  ) { }

  ngOnInit(): void {
    try {
      this.tipUtilizator = localStorage.getItem('utilizator')||"v";
      if (localStorage.getItem('localUser') != null && this.tipUtilizator!='v') {
        this.autentificareForm.setValue({ 'numeUtilizator': atob(localStorage.getItem('localUser') || "").split(':')[0], 'parolaUtilizator': "" })
      }
    }
    catch (eroare) {
      //console.log("in pagina de autentificare: " + eroare);
    }
  }

  autentificare(): void {
    if (this.autentificareForm.valid) {
      this.utilizator = this.getUtilizator();
      this.verificareUtilizator();
    } else {
      this.mesajFeedback = 'Completați toate câmpurile';
    }
  }
  private getUtilizator(): Utilizator {
    return {
      numeUtilizator: this.autentificareForm.get(['numeUtilizator'])?.value,
      parola: this.autentificareForm.get(['parolaUtilizator'])?.value,
    };
  }
  private verificareUtilizator(): void {
    if (this.utilizator != null) {
      this.autentificareService
        .autentificare(this.utilizator)
        .subscribe((u) => {
          localStorage.setItem('utilizator', u);
          if (u && this.utilizator != undefined) {
            localStorage.setItem(
              'localUser',
              btoa(
                this.utilizator.numeUtilizator + `:` + this.utilizator.parola
              )
            );
            //console.log(this.utilizator);
            this.routeToLocation();
          } else {
            this.mesajFeedback =
              'Datele de conectare sunt incorecte sau utilizatorul nu există';
            localStorage.setItem('localUser', btoa(`vizitator:parola`));
          }
        });
    }
  }

  private routeToLocation() {
    let rang = localStorage.getItem('utilizator');
    let locatie = localStorage.getItem('pagina-actuala') || '';
    switch (locatie) {
      case 'noutati-preview': {
        if (rang != null && rang != 'v') {
          this.router.navigate([`/noutati/noutatiAdmin`]);
        } else {
          this.router.navigate(['']);
        }
        break;
      }
      case 'noutate-detaliat': {
        if (rang != null && rang != 'v') {
          this.router.navigate([`/noutati/noutateCreareEditare`]);
        } else {
          this.router.navigate(['']);
        }
        break;
      }
      default: {
        this.router.navigate(['']);
        break;
      }
    }
  }
  deconectare() {
    this.autentificareService.delogare();
    this.mesajFeedback = "Delogare realizată cu succes!";
  }
  deconectare2() {
    let aux: Utilizator = {
      'numeUtilizator': 'vizitator',
      'parola': 'parola'
    }
    this.autentificareService.delogare2(aux).subscribe((u) => {
      localStorage.setItem('utilizator', u);
      if (u && this.utilizator != undefined) {
        localStorage.setItem(
          'localUser',
          btoa(
            this.utilizator.numeUtilizator + `:` + this.utilizator.parola
          )
        );
        //console.log(this.utilizator);
        this.tipUtilizator = localStorage.getItem('utilizator')||"v";
        this.routeToLocation();
      } else {
        this.mesajFeedback = 'Delogare cu succes';
        this.autentificareForm.setValue({ 'numeUtilizator': "", 'parolaUtilizator': "" });
        localStorage.setItem('localUser', btoa(`vizitator:parola`));
      }
    });
  }
}
