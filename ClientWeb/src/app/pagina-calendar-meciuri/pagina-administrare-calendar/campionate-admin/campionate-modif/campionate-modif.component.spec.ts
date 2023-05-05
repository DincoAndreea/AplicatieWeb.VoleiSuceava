import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CampionateModifComponent } from './campionate-modif.component';

describe('CampionateModifComponent', () => {
  let component: CampionateModifComponent;
  let fixture: ComponentFixture<CampionateModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CampionateModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CampionateModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
