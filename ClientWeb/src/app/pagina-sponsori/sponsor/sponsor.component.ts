import { Component, Input, OnInit } from '@angular/core';
import { ISponsor } from 'src/app/model/sponsor';
import { SponsoriService } from '../service/SponsoriService';

@Component({
  selector: 'app-sponsor',
  templateUrl: './sponsor.component.html',
  styleUrls: ['./sponsor.component.css'],
})
export class SponsorComponent implements OnInit {
  @Input() urlLink: string = ``;
  @Input() logo: string = ``;
  @Input() nume: string = ``;
  @Input() editie: string = ``;

  ngOnInit(): void {
    
  }

  //momentan nu folosesc asta
  routeExemplu() {}
}
