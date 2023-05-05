import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeciuriAdaugareComponent } from './meciuri-adaugare.component';

describe('MeciuriAdaugareComponent', () => {
  let component: MeciuriAdaugareComponent;
  let fixture: ComponentFixture<MeciuriAdaugareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeciuriAdaugareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MeciuriAdaugareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
