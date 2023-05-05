import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrototipNavBarComponent } from './prototip-nav-bar.component';

describe('PrototipNavBarComponent', () => {
  let component: PrototipNavBarComponent;
  let fixture: ComponentFixture<PrototipNavBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrototipNavBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrototipNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
