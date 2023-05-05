import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeciuriModifComponent } from './meciuri-modif.component';

describe('MeciuriModifComponent', () => {
  let component: MeciuriModifComponent;
  let fixture: ComponentFixture<MeciuriModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeciuriModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MeciuriModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
