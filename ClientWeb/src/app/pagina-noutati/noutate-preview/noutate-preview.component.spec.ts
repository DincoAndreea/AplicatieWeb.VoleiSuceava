import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoutatePreviewComponent } from './noutate-preview.component';

describe('NoutatePreviewComponent', () => {
  let component: NoutatePreviewComponent;
  let fixture: ComponentFixture<NoutatePreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoutatePreviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoutatePreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
