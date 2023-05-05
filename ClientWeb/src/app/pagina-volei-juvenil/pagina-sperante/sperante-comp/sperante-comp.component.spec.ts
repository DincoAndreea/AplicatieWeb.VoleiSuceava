import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SperanteCompComponent } from './sperante-comp.component';

describe('SperanteCompComponent', () => {
  let component: SperanteCompComponent;
  let fixture: ComponentFixture<SperanteCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SperanteCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SperanteCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
