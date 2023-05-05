import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { IImaginiSubsol } from 'src/app/model/ImaginiSubsol';
import { SubsolService } from './SubsolService';
import { Router, NavigationEnd} from '@angular/router';
import { Constante } from '../constante/constante';
@Component({
  selector: 'app-subsol',
  templateUrl: './subsol.component.html',
  styleUrls: ['./subsol.component.css']
})
export class SubsolComponent implements OnInit, OnDestroy {
  currentRoute:string;
  link:string=Constante.paginaDeVoleiDeFacebookACSMSuceava;
  logo: IImaginiSubsol[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getSponsori: SubsolService, private router: Router) { 
    this.unsubscribe$ = new Subject<void>();
    this.logo;

    //console.log(router.url);
    this.currentRoute=router.url;
  }

  ngOnInit(): void {
    this.getSponsori
    .getSponsori()
    .subscribe((logos)=>{
      this.logo = JSON.parse(JSON.stringify(logos));
      //console.dir(this.logo);
      // while(this.logo.length<5)
      // {
      //   this.logo.push(JSON.parse(JSON.stringify('assets/sponsors-welcome.png')));
      // }
    });
  }
  ngOnDestroy(): void{
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

}
