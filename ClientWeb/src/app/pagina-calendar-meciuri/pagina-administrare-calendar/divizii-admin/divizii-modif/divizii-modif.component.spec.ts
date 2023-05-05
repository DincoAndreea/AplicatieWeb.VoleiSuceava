import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiviziiModifComponent } from './divizii-modif.component';

describe('DiviziiModifComponent', () => {
  let component: DiviziiModifComponent;
  let fixture: ComponentFixture<DiviziiModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiviziiModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiviziiModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
