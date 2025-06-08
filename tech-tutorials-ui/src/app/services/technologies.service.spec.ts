import { TestBed } from '@angular/core/testing';

import { TechnologiesService } from './technologies.service';
import {HttpTestingController, provideHttpClientTesting} from '@angular/common/http/testing';
import {provideHttpClient} from '@angular/common/http';

describe('TechnologiesService', () => {
  let service: TechnologiesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TechnologiesService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(TechnologiesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch technologies via GET', () => {
    const mockTechnologies = [{ id: 1, name: 'Angular' }, { id: 2, name: 'React' }];

    service.getTechnologies().subscribe(data => {
      expect(data).withContext("Technologies should be equal").toEqual(mockTechnologies);
      expect(data.length).withContext("Technologies length should be equal").toEqual(2);
      expect(data[0].name).withContext("Technologies Name should match").toEqual('Angular');
      expect(data[1].name).withContext("Technologies Name should match").toEqual('React');
    });

    const req = httpMock.expectOne('http://localhost:3000/technologies');
    expect(req.request.method).toBe('GET');
    req.flush(mockTechnologies);
  });
});
