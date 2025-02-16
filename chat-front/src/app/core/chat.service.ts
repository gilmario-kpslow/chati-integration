import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { getHost } from './host-resolve';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private http = inject(HttpClient);

  private url = getHost();

  save(obj: any) {
    return this.http.post(`${this.url}registro`, obj);
  }

  listar() {
    return this.http.get<any>(`${this.url}registro`);
  }

  delete(id: string) {
    return this.http.delete(`${this.url}registro/${id}`);
  }

  getPorId(id: string) {
    return this.http.get(`${this.url}registro/getid/${id}`);
  }

  executar(id: string, params: string) {
    return this.http.get(`${this.url}registro/executar/${id}${params}`);
  }

  notificar(id: string, params: any) {
    return this.http.get(`${this.url}registro/notificar/${id}?${params}`);
  }

  ativar(id: string) {
    return this.http.get(`${this.url}registro/ativar/${id}`);
  }
}
