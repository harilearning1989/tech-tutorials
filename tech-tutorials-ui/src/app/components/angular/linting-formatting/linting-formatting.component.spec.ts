import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LintingFormattingComponent } from './linting-formatting.component';

describe('LintingFormattingComponent', () => {
  let component: LintingFormattingComponent;
  let fixture: ComponentFixture<LintingFormattingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LintingFormattingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LintingFormattingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
