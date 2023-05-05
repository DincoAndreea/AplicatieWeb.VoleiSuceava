import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoutateDetaliatComponent } from './noutate-detaliat.component';

describe('NoutateDetaliatComponent', () => {
  let component: NoutateDetaliatComponent;
  let fixture: ComponentFixture<NoutateDetaliatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoutateDetaliatComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoutateDetaliatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
