import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CliNewCreationComponent } from './cli-new-creation.component';

describe('CliNewCreationComponent', () => {
  let component: CliNewCreationComponent;
  let fixture: ComponentFixture<CliNewCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CliNewCreationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CliNewCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
