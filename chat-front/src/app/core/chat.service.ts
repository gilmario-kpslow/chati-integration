import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../environments/environment';
import { Chat } from './chat.model';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private http = inject(HttpClient);

  private url = environment.url;

  save(obj: any) {
    return this.http.post(`${this.url}/registro`, obj);
  }

  listar() {
    return this.http.get<any>(`${this.url}/registro`);
  }
}
