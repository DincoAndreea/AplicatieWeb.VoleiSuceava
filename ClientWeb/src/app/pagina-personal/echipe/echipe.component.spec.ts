import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EchipeComponent } from './echipe.component';

describe('EchipeComponent', () => {
  let component: EchipeComponent;
  let fixture: ComponentFixture<EchipeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EchipeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EchipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
