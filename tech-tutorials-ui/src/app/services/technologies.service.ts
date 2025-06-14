import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Technology} from '../models/technology';
import {LoggerService} from './logger.service';

@Injectable({
  providedIn: 'root'
})
export class TechnologiesService {

  private apiUrl = 'http://localhost:3000/technologies';

  constructor(private http: HttpClient,
              private loggerService: LoggerService) {}

  getTechnologies(): Observable<Technology[]> {
    this.loggerService.warn('Fetching technologies from API');
    return this.http.get<Technology[]>(this.apiUrl);
  }

  getTechnologyById(id: string): Observable<Technology> {
    this.loggerService.info(`Fetching technology with ID: ${id}`);
    return this.http.get<Technology>(`${this.apiUrl}/${id}`);
  }

  updateTechnology(id: string, technology: Technology): Observable<Technology> {
    this.loggerService.log(`Updating technology with ID: ${id}`, technology);
    return this.http.put<Technology>(`${this.apiUrl}/${id}`, technology);
  }

  addTechnology(technology: Technology): Observable<Technology> {
    this.loggerService.log('Adding new technology', technology);
    return this.http.post<Technology>(this.apiUrl, technology);
  }

}
