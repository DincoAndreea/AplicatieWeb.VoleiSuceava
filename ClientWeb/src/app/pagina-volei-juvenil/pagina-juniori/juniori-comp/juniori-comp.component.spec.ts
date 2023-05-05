import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JunioriCompComponent } from './juniori-comp.component';

describe('JunioriCompComponent', () => {
  let component: JunioriCompComponent;
  let fixture: ComponentFixture<JunioriCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JunioriCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JunioriCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
