import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestApiAuthenticationComponent } from './rest-api-authentication.component';

describe('RestApiAuthenticationComponent', () => {
  let component: RestApiAuthenticationComponent;
  let fixture: ComponentFixture<RestApiAuthenticationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RestApiAuthenticationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestApiAuthenticationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
