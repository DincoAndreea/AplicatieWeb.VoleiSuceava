import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViziuneComponent } from './viziune.component';

describe('ViziuneComponent', () => {
  let component: ViziuneComponent;
  let fixture: ComponentFixture<ViziuneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViziuneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViziuneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
