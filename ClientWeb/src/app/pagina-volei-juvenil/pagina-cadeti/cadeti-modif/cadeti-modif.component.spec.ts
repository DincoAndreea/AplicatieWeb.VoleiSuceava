import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadetiModifComponent } from './cadeti-modif.component';

describe('CadetiModifComponent', () => {
  let component: CadetiModifComponent;
  let fixture: ComponentFixture<CadetiModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadetiModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadetiModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
