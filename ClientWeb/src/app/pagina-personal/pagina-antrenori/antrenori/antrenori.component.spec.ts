import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AntrenoriComponent } from './antrenori.component';

describe('AntrenoriComponent', () => {
  let component: AntrenoriComponent;
  let fixture: ComponentFixture<AntrenoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AntrenoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AntrenoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
