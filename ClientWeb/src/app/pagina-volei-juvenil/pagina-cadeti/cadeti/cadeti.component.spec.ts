import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadetiComponent } from './cadeti.component';

describe('CadetiComponent', () => {
  let component: CadetiComponent;
  let fixture: ComponentFixture<CadetiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadetiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadetiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
