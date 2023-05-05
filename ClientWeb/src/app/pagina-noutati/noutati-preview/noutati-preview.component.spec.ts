import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoutatiPreviewComponent } from './noutati-preview.component';

describe('NoutatiPreviewComponent', () => {
  let component: NoutatiPreviewComponent;
  let fixture: ComponentFixture<NoutatiPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoutatiPreviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoutatiPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
