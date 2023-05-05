import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiviziiAdaugareComponent } from './divizii-adaugare.component';

describe('DiviziiAdaugareComponent', () => {
  let component: DiviziiAdaugareComponent;
  let fixture: ComponentFixture<DiviziiAdaugareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiviziiAdaugareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiviziiAdaugareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
