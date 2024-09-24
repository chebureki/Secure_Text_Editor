import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EncryptionService {

  private apiUrl = '/api/encrypt';

  constructor(private http: HttpClient) { }

  // Send the text to be encrypted
  encryptText(text: string): Observable<string> {
    return this.http.post(this.apiUrl, text, { responseType: 'text' });
  }
}
