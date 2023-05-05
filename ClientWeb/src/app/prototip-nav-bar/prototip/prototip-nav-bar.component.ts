import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-prototip-nav-bar',
  templateUrl: './prototip-nav-bar.component.html',
  styleUrls: ['./prototip-nav-bar.component.css'],
})
export class PrototipNavBarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  butonAdminDinamic: string = "";
  send(butonAdminDinamic: string) {
    if (butonAdminDinamic == "acasa") {
      this.butonAdminDinamic = "AUTENTIFICARE";
    } else {
      this.butonAdminDinamic = "ADMINISTRARE PAGINĂ"
    }
    //console.log("in parinte, avem butonul " + butonAdminDinamic+" si trimitem "+this.butonAdminDinamic);
  }

}
