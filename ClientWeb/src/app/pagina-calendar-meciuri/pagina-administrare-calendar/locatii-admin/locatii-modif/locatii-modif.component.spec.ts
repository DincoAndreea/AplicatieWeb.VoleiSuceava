import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LocatiiModifComponent } from './locatii-modif.component';

describe('LocatiiModifComponent', () => {
  let component: LocatiiModifComponent;
  let fixture: ComponentFixture<LocatiiModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LocatiiModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LocatiiModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
