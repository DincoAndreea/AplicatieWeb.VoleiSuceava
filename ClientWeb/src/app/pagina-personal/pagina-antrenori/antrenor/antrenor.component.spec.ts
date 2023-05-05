import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AntrenorComponent } from './antrenor.component';

describe('AntrenorComponent', () => {
  let component: AntrenorComponent;
  let fixture: ComponentFixture<AntrenorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AntrenorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AntrenorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
