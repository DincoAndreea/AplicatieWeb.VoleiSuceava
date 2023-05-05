import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SenioriModifComponent } from './seniori-modif.component';

describe('SenioriModifComponent', () => {
  let component: SenioriModifComponent;
  let fixture: ComponentFixture<SenioriModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SenioriModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SenioriModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
