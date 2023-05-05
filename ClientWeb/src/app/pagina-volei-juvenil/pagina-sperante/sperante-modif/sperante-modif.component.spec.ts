import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SperanteModifComponent } from './sperante-modif.component';

describe('SperanteModifComponent', () => {
  let component: SperanteModifComponent;
  let fixture: ComponentFixture<SperanteModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SperanteModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SperanteModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
