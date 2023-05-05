import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadetiCompComponent } from './cadeti-comp.component';

describe('CadetiCompComponent', () => {
  let component: CadetiCompComponent;
  let fixture: ComponentFixture<CadetiCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadetiCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadetiCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
