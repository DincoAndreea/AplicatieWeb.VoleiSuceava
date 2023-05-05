import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CampionateAdaugareComponent } from './campionate-adaugare.component';

describe('CampionateAdaugareComponent', () => {
  let component: CampionateAdaugareComponent;
  let fixture: ComponentFixture<CampionateAdaugareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CampionateAdaugareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CampionateAdaugareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
