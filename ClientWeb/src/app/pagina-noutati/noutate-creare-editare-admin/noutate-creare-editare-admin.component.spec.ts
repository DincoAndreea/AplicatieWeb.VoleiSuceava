import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoutateCreareEditareAdminComponent } from './noutate-creare-editare-admin.component';

describe('NoutateCreareEditareAdminComponent', () => {
  let component: NoutateCreareEditareAdminComponent;
  let fixture: ComponentFixture<NoutateCreareEditareAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoutateCreareEditareAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoutateCreareEditareAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
