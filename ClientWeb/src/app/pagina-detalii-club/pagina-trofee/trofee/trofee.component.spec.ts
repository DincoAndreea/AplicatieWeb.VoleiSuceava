import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrofeeComponent } from './trofee.component';

describe('TrofeeComponent', () => {
  let component: TrofeeComponent;
  let fixture: ComponentFixture<TrofeeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrofeeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrofeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
