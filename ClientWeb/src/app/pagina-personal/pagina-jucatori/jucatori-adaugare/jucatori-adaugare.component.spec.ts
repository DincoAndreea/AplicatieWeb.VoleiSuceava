import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JucatoriAdaugareComponent } from './jucatori-adaugare.component';

describe('JucatoriAdaugareComponent', () => {
  let component: JucatoriAdaugareComponent;
  let fixture: ComponentFixture<JucatoriAdaugareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JucatoriAdaugareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JucatoriAdaugareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
