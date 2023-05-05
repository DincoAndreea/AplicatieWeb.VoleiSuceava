import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AntrenoriAdaugareComponent } from './antrenori-adaugare.component';

describe('AntrenoriAdaugareComponent', () => {
  let component: AntrenoriAdaugareComponent;
  let fixture: ComponentFixture<AntrenoriAdaugareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AntrenoriAdaugareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AntrenoriAdaugareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
