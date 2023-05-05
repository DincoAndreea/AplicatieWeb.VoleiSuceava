import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SiglaNavBarComponent } from './sigla-nav-bar.component';

describe('SiglaNavBarComponent', () => {
  let component: SiglaNavBarComponent;
  let fixture: ComponentFixture<SiglaNavBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SiglaNavBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SiglaNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
