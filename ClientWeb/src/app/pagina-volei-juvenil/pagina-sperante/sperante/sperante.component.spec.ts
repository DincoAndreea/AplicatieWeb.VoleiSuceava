import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SperanteComponent } from './sperante.component';

describe('SperanteComponent', () => {
  let component: SperanteComponent;
  let fixture: ComponentFixture<SperanteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SperanteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SperanteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
