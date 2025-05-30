import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BootstrapSetupComponent } from './bootstrap-setup.component';

describe('BootstrapSetupComponent', () => {
  let component: BootstrapSetupComponent;
  let fixture: ComponentFixture<BootstrapSetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BootstrapSetupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BootstrapSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
