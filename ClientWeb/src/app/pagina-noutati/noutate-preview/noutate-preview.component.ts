import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-noutate-preview',
  templateUrl: './noutate-preview.component.html',
  styleUrls: ['./noutate-preview.component.css']
})
export class NoutatePreviewComponent implements OnInit {
  @Input() id: number = 0;
  @Input() imagine: String = ``;
  @Input() titlu: String = ``;
  @Input() descriere: String = ``;
  constructor() { }

  ngOnInit(): void {
  }

}
