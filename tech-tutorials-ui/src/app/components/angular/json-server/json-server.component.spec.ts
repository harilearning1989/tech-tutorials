import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JsonServerComponent } from './json-server.component';

describe('JsonServerComponent', () => {
  let component: JsonServerComponent;
  let fixture: ComponentFixture<JsonServerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JsonServerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JsonServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
