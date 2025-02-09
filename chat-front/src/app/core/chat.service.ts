import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  http = inject(HttpClient);

  url = environment.url;

  save(obj: any) {
    return this.http.post(`${this.url}/registro`, obj);
  }
}
