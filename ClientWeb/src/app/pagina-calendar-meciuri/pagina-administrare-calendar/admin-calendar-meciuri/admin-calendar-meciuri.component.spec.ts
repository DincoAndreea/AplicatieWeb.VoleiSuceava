import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCalendarMeciuriComponent } from './admin-calendar-meciuri.component';

describe('AdminCalendarMeciuriComponent', () => {
  let component: AdminCalendarMeciuriComponent;
  let fixture: ComponentFixture<AdminCalendarMeciuriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCalendarMeciuriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCalendarMeciuriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
