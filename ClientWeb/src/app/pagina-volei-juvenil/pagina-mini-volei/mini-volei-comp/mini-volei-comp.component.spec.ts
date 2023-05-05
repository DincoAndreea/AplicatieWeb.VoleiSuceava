import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiniVoleiCompComponent } from './mini-volei-comp.component';

describe('MiniVoleiCompComponent', () => {
  let component: MiniVoleiCompComponent;
  let fixture: ComponentFixture<MiniVoleiCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MiniVoleiCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MiniVoleiCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
