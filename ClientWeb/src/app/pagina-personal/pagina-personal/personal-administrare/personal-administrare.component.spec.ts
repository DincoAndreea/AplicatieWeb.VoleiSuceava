import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalAdministrareComponent } from './personal-administrare.component';

describe('PersonalAdministrareComponent', () => {
  let component: PersonalAdministrareComponent;
  let fixture: ComponentFixture<PersonalAdministrareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalAdministrareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonalAdministrareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
