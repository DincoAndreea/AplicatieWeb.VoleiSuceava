import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EchipeAdaugareComponent } from './echipe-adaugare.component';

describe('EchipeAdaugareComponent', () => {
  let component: EchipeAdaugareComponent;
  let fixture: ComponentFixture<EchipeAdaugareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EchipeAdaugareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EchipeAdaugareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
