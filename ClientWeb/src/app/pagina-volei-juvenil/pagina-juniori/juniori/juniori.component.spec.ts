import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JunioriComponent } from './juniori.component';

describe('JunioriComponent', () => {
  let component: JunioriComponent;
  let fixture: ComponentFixture<JunioriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JunioriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JunioriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
