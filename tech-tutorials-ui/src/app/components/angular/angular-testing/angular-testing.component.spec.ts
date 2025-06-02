import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AngularTestingComponent } from './angular-testing.component';

describe('AngularTestingComponent', () => {
  let component: AngularTestingComponent;
  let fixture: ComponentFixture<AngularTestingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AngularTestingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AngularTestingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
