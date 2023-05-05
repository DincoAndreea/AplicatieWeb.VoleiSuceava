import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarMeciuriComponent } from './calendar-meciuri.component';

describe('CalendarMeciuriComponent', () => {
  let component: CalendarMeciuriComponent;
  let fixture: ComponentFixture<CalendarMeciuriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarMeciuriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalendarMeciuriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
