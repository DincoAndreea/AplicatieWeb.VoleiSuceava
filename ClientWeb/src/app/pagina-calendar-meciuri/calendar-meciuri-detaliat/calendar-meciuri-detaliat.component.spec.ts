import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarMeciuriDetaliatComponent } from './calendar-meciuri-detaliat.component';

describe('CalendarMeciuriDetaliatComponent', () => {
  let component: CalendarMeciuriDetaliatComponent;
  let fixture: ComponentFixture<CalendarMeciuriDetaliatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarMeciuriDetaliatComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalendarMeciuriDetaliatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
