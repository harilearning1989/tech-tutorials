import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TechnologiesService {

  private apiUrl = 'http://localhost:3000/technologies';

  constructor(private http: HttpClient) {}

  getTechnologies(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

}
