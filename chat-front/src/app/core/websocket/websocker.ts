import { EventEmitter, Injectable } from '@angular/core';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';
import { Observable, Subscription, tap, throwError, timer } from 'rxjs';
import { v4 as uuidv4, v6 as uuidv6 } from 'uuid';
import { getWebSocketHost } from '../host-resolve';
import { Comando } from './comando';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private socket$: WebSocketSubject<Comando>;
  private sub?: Subscription;

  private url = getWebSocketHost();
  event = new EventEmitter();

  constructor() {
    this.socket$ = webSocket(`${this.url}/chat/${uuidv4()}`);
    this.init();
  }

  init() {
    this.socket$ = webSocket(`${this.url}/chat/${uuidv4()}`);
    this.sub?.unsubscribe();
    this.sub = this.socket$.subscribe({
      error: err => {
        console.log("ERROR => ", err)
        timer(5000).subscribe(() => this.init());
      }
    });
    this.event.emit();
  }

  sendMessage(message: Comando) {
    this.socket$.next(message);
  }

  getMessages(): Observable<Comando> {
    return this.socket$.asObservable().pipe(tap(a => console.log("Mensagem => ", a)));
  }

  closeConnection() {
    this.socket$.complete();
  }

}
