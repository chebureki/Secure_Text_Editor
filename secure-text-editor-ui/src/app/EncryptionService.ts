import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EncryptionService {

  private apiEncrypt = '/api/encrypt';
  private apiDecrypt = '/api/decrypt';

  constructor(private http: HttpClient) { }

  // Send the text and encryption parameters to the backend
  encryptText(payload: any): Observable<string> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log('Payload:', payload);
    return this.http.post(this.apiEncrypt, payload, { headers: headers, responseType: 'text' });
  }

  decryptText(payload: any): Observable<string>{
    return this.http.post(this.apiDecrypt, payload, {  responseType: 'text' });
  }

}
