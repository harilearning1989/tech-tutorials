import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AopComponent } from './aop.component';

describe('AopComponent', () => {
  let component: AopComponent;
  let fixture: ComponentFixture<AopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AopComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
