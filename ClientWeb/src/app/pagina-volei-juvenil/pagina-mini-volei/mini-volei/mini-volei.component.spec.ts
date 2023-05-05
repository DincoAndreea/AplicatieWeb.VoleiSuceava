import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiniVoleiComponent } from './mini-volei.component';

describe('MiniVoleiComponent', () => {
  let component: MiniVoleiComponent;
  let fixture: ComponentFixture<MiniVoleiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MiniVoleiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MiniVoleiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
