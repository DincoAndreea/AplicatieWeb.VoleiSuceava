import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JucatorComponent } from './jucator.component';

describe('JucatorComponent', () => {
  let component: JucatorComponent;
  let fixture: ComponentFixture<JucatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JucatorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JucatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
