import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SponsoriComponent } from './sponsori.component';

describe('SponsoriComponent', () => {
  let component: SponsoriComponent;
  let fixture: ComponentFixture<SponsoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SponsoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SponsoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
