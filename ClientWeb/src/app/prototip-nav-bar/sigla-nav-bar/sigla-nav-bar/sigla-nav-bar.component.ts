import { Component, DoCheck, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { delay, of } from 'rxjs';

@Component({
  selector: 'app-sigla-nav-bar',
  templateUrl: './sigla-nav-bar.component.html',
  styleUrls: ['./sigla-nav-bar.component.css']
})
export class SiglaNavBarComponent implements OnInit {
  @Input() dataInput: string = "";
  @Input() show: boolean = true;
  butonAdminDinamic: string = "ADMINISTRARE PAGINA";
  utilizatorActiv: string="";
  constructor(private router: Router) {
  }

  ngOnInit(): void {
    //this.actualizareButonAdmin();
    const foo = of([1])//mecanism de adaugare a unui delay
      .pipe(delay(100))
      .subscribe(subscriber => {
        //console.log("ngOnInit: "+localStorage.getItem("pagina-actuala"));
        if (localStorage.getItem("pagina-actuala") == "acasa") {
          this.butonAdminDinamic = "AUTENTIFICARE";
        }
        else {
          this.butonAdminDinamic = "ADMINISTRARE PAGINA";
        }
      });
    
    let locatie = localStorage.getItem("pagina-actuala");
    //if(locatie == "viziune" || locatie == "trofee" || locatie == "istoric" || locatie == "sponsori")
    if(locatie == "noutateCreareEditare")
    {
      const btn = document.getElementById("btnadmin");
      btn?.style.setProperty('visibility','hidden');
    }
    else
    {
      const btn = document.getElementById("btnadmin");
      btn?.style.setProperty('visibility','visible');
    }
    //active user
    //console.log(localStorage.getItem('utilizator'));
    
    if(localStorage.getItem("utilizator")=='a')
    {
      this.utilizatorActiv="Administrator";
    }
    if(localStorage.getItem("utilizator")=='c')
    {
      this.utilizatorActiv="Creator de conținut";
    }
    if(localStorage.getItem("utilizator")=='v')
    {
      this.utilizatorActiv="Vizitator";
    }
  }

  onClickEvent(event: Event) {
    let rang = localStorage.getItem("utilizator");
    let locatie = localStorage.getItem("pagina-actuala") || "";
    //console.log("in sigla: "+locatie);
    
    switch (locatie) {
      case "noutati-preview":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/noutati/noutatiAdmin`]);
          }
          else {
            this.router.navigate(['/autentificare']);
          }
          break;
        }
      case "noutate-detaliat":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/noutati/noutateCreareEditare`]);
          }
          else {
            this.router.navigate([`/autentificare`]);
          }
          break;
        }
      case "cadeti":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/cadeti/voleijuvenil`])
          }
          else {
            this.router.navigate([`/autentificare`]); 
          }
          break;
        }
      case "juniori":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/juniori/voleijuvenil`])
          }
          else {
            this.router.navigate([`/autentificare`]);
          }
          break;
        }
      case "sperante":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/sperante/voleijuvenil`])
          }
          else {
            this.router.navigate([`/autentificare`]);
          }
          break;
        }
      case "minivolei":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/minivolei/voleijuvenil`])
          }
          else {
            this.router.navigate([`/autentificare`]);
          }
          break;
        }
      case "personal":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/personal/personaladmin`])
          }
          else {
            this.router.navigate([`/autentificare`]);
          }
          break;
        }
      case "calendar":
        {
          if (rang != null && rang != "v") {
            this.router.navigate([`/calendar/adminCalendar`])
          }
          else {
            this.router.navigate([`/autentificare`]);
          }
          break;
        }
      default:
        {
          this.router.navigate([`/autentificare`]);
          break;
        }
    }
  }

  actualizareButonAdmin() {
    if (this.dataInput != null && this.dataInput != undefined && this.dataInput != "") {
      this.butonAdminDinamic = this.dataInput;
    }
  }
}
