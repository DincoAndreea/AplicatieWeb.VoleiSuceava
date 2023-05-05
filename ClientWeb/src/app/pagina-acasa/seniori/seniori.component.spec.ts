import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SenioriComponent } from './seniori.component';

describe('SenioriComponent', () => {
  let component: SenioriComponent;
  let fixture: ComponentFixture<SenioriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SenioriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SenioriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
