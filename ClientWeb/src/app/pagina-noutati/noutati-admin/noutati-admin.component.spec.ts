import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoutatiAdminComponent } from './noutati-admin.component';

describe('NoutatiAdminComponent', () => {
  let component: NoutatiAdminComponent;
  let fixture: ComponentFixture<NoutatiAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoutatiAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoutatiAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
