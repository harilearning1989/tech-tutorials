import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstallSetupComponent } from './install-setup.component';

describe('InstallSetupComponent', () => {
  let component: InstallSetupComponent;
  let fixture: ComponentFixture<InstallSetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InstallSetupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InstallSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
