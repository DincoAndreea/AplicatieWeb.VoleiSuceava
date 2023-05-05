import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaAdministrareConturiComponent } from './pagina-administrare-conturi.component';

describe('PaginaAdministrareConturiComponent', () => {
  let component: PaginaAdministrareConturiComponent;
  let fixture: ComponentFixture<PaginaAdministrareConturiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginaAdministrareConturiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaAdministrareConturiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
