import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpringIntroductionComponent } from './spring-introduction.component';

describe('SpringIntroductionComponent', () => {
  let component: SpringIntroductionComponent;
  let fixture: ComponentFixture<SpringIntroductionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpringIntroductionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpringIntroductionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
