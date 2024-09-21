import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private apiUrl = '/api';

  constructor(private http: HttpClient) { }

  getSecureData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/secure`);
  }
}
