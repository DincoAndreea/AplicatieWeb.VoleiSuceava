import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pagini-nav-bar',
  templateUrl: './pagini-nav-bar.component.html',
  styleUrls: ['./pagini-nav-bar.component.css']
})
export class PaginiNavBarComponent implements OnInit {
  constructor(private router: Router) {
  }

  private dubluClick: number = 0;

  @Output() emitter: EventEmitter < string >= new EventEmitter<string>();

  ngOnInit(): void {
  }

  actualizareAutentificare(data: string) {
    this.emitter.emit(data);
    //console.log("In pagini, trimitem o data "+data);
  }
}
