import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarMeciuriPreviewComponent } from './calendar-meciuri-preview.component';

describe('CalendarMeciuriPreviewComponent', () => {
  let component: CalendarMeciuriPreviewComponent;
  let fixture: ComponentFixture<CalendarMeciuriPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarMeciuriPreviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalendarMeciuriPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
