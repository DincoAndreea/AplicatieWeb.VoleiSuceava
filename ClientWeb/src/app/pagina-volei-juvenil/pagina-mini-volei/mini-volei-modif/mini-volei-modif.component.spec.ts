import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiniVoleiModifComponent } from './mini-volei-modif.component';

describe('MiniVoleiModifComponent', () => {
  let component: MiniVoleiModifComponent;
  let fixture: ComponentFixture<MiniVoleiModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MiniVoleiModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MiniVoleiModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
