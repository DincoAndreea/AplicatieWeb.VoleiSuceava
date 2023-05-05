import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginiNavBarComponent } from './pagini-nav-bar.component';

describe('PaginiNavBarComponent', () => {
  let component: PaginiNavBarComponent;
  let fixture: ComponentFixture<PaginiNavBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginiNavBarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginiNavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
