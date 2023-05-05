import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocatiiAdaugareComponent } from './locatii-adaugare.component';

describe('LocatiiAdaugareComponent', () => {
  let component: LocatiiAdaugareComponent;
  let fixture: ComponentFixture<LocatiiAdaugareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocatiiAdaugareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LocatiiAdaugareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
