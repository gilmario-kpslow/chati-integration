import { Injectable } from '@angular/core';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';
import { Observable } from 'rxjs';
import { v4 as uuidv4, v6 as uuidv6 } from 'uuid';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private socket$: WebSocketSubject<any>;

  constructor() {
    const proto = location.protocol == 'http:' ? 'ws://' : 'wss://';
    const host = location.host.replace('4200', '8080');

    console.log(host, proto);

    this.socket$ = webSocket(`${proto}${host}/chat/${uuidv4()}`);
  }

  // Send a message to the server
  sendMessage(message: any) {
    this.socket$.next(message);
  }

  // Receive messages from the server
  getMessages(): Observable<any> {
    return this.socket$.asObservable();
  }

  // Close the WebSocket connection
  closeConnection() {
    this.socket$.complete();
  }
}
