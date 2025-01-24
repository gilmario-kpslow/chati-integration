import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  http = inject(HttpClient);

  constructor() { }


  save(obj: any) {
    return this.http.post(`registro`, obj);
  }
}
