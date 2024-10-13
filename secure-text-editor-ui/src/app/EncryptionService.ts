import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EncryptionService {

  private apiUrl = '/api/encrypt';

  constructor(private http: HttpClient) { }

  // Send the text and encryption parameters to the backend
  encryptText(payload: any): Observable<string> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.apiUrl, payload, { headers: headers, responseType: 'text' });
  }

}
