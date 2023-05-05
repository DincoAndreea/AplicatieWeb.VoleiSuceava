import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaAutentificareComponent } from './pagina-autentificare.component';

describe('PaginaAutentificareComponent', () => {
  let component: PaginaAutentificareComponent;
  let fixture: ComponentFixture<PaginaAutentificareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginaAutentificareComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaAutentificareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
