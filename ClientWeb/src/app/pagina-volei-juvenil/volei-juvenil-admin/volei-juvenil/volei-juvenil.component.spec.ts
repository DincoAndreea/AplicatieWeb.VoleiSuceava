import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoleiJuvenilComponent } from './volei-juvenil.component';

describe('VoleiJuvenilComponent', () => {
  let component: VoleiJuvenilComponent;
  let fixture: ComponentFixture<VoleiJuvenilComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoleiJuvenilComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoleiJuvenilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
