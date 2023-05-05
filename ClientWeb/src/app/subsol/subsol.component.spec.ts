import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubsolComponent } from './subsol.component';

describe('SubsolComponent', () => {
  let component: SubsolComponent;
  let fixture: ComponentFixture<SubsolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubsolComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubsolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
