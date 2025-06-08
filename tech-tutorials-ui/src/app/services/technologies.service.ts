import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Technology} from '../models/technology';

@Injectable({
  providedIn: 'root'
})
export class TechnologiesService {

  private apiUrl = 'http://localhost:3000/technologies';

  constructor(private http: HttpClient) {}

  getTechnologies(): Observable<Technology[]> {
    return this.http.get<Technology[]>(this.apiUrl);
  }

}
