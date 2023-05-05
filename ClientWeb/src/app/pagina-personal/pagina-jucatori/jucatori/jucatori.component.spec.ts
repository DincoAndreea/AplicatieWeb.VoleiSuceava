import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JucatoriComponent } from './jucatori.component';

describe('JucatoriComponent', () => {
  let component: JucatoriComponent;
  let fixture: ComponentFixture<JucatoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JucatoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JucatoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
