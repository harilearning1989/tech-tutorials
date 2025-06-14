import {TestBed} from '@angular/core/testing';
import mockTechnologiesJson from '../../assets/mock-technologies.json';


import {TechnologiesService} from './technologies.service';
import {HttpTestingController, provideHttpClientTesting} from '@angular/common/http/testing';
import {provideHttpClient} from '@angular/common/http';
import {Technology} from '../models/technology';

describe('TechnologiesService', () => {
  let technologiesService: TechnologiesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TechnologiesService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    technologiesService = TestBed.inject(TechnologiesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(technologiesService).toBeTruthy();
  });

  it('should fetch technologies via GET', () => {
    const mockTechnologies = [
      {
        "id": "angular",
        "name": "Angular",
        "topics": [
          {
            "name": "Introduction",
            "route": "/angular/introduction"
          },
          {
            "name": "Install&Setup",
            "route": "/angular/setup"
          }]
      }, {
        "id": "java",
        "name": "Java",
        "topics": [
          {
            "name": "Basics",
            "route": "/java/basics"
          },
          {
            "name": "OOP",
            "route": "/java/oop"
          }
        ]
      }];

    technologiesService.getTechnologies().subscribe(data => {
      expect(data).withContext("Technologies should be equal").toEqual(mockTechnologies);
      expect(data.length).withContext("Technologies length should be equal").toEqual(2);
      expect(data[0].name).withContext("Technologies Name should match").toEqual('Angular');
      expect(data[1].name).withContext("Technologies Name should match").toEqual('Java');
    });

    const req = httpMock.expectOne('http://localhost:3000/technologies');
    expect(req.request.method).toBe('GET');
    req.flush(mockTechnologies);
  });

  it('should fetch technologies via GET', () => {
    technologiesService.getTechnologies().subscribe(data => {
      expect(data).toEqual(mockTechnologiesJson);
    });

    const req = httpMock.expectOne('http://localhost:3000/technologies');
    expect(req.request.method).toBe('GET');
    req.flush(mockTechnologiesJson);
  });

  it('should fetch technology by id via GET', () => {
    const mockTechnology: any = mockTechnologiesJson.slice(0, 2);

    technologiesService.getTechnologyById('1').subscribe(data => {
      expect(data).withContext('Should be match with the request').toEqual(mockTechnology);
    });

    const req = httpMock.expectOne('http://localhost:3000/technologies/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockTechnology);
  });

  it('should update technology via PUT', () => {
    const updatedTech: Technology = {id: '1', name: 'Angular Updated', topics: []};

    technologiesService.updateTechnology('1', updatedTech).subscribe(data => {
      expect(data).toEqual(updatedTech);
    });

    const req = httpMock.expectOne('http://localhost:3000/technologies/1');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedTech);
    req.flush(updatedTech);
  });

  it('should add technology via POST', () => {
    const newTech: Technology = {id: '3', name: 'Vue', topics: []};

    technologiesService.addTechnology(newTech).subscribe(data => {
      expect(data).toEqual(newTech);
    });

    const req = httpMock.expectOne('http://localhost:3000/technologies');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newTech);
    req.flush(newTech);
  });

});
