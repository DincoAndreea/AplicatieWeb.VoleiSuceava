import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { ISponsor } from 'src/app/model/sponsor';
import { SponsoriService } from '../service/SponsoriService';
import { SponsorComponent } from '../sponsor/sponsor.component';
@Component({
  selector: 'app-sponsori',
  templateUrl: './sponsori.component.html',
  styleUrls: ['./sponsori.component.css'],
})
export class SponsoriComponent implements OnInit, OnDestroy {
  sponsori: ISponsor[] = [];

  unsubscribe$: Subject<void>;

  constructor(private readonly getSponsori: SponsoriService) {
    this.unsubscribe$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.getSponsori.getSponsori().subscribe((sponsori) => {
      this.sponsori = JSON.parse(JSON.stringify(sponsori));
      //console.dir(this.sponsori);
      localStorage.setItem("pagina-actuala","sponsori");
    });
  }
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
