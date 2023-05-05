import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JunioriModifComponent } from './juniori-modif.component';

describe('JunioriModifComponent', () => {
  let component: JunioriModifComponent;
  let fixture: ComponentFixture<JunioriModifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JunioriModifComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JunioriModifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
