import { Injectable } from '@angular/core';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';
import { Observable } from 'rxjs';
import { v4 as uuidv4, v6 as uuidv6 } from 'uuid';
import { getWebSocketHost } from './host-resolve';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private socket$: WebSocketSubject<string>;

  private url = getWebSocketHost();

  constructor() {


    this.socket$ = webSocket(`${this.url}/chat/${uuidv4()}`);
  }

  // Send a message to the server
  sendMessage(message: any) {
    console.log(message)
    this.socket$.next(message);
  }

  // Receive messages from the server
  getMessages(): Observable<any> {
    console.log("getMensagens")
    return this.socket$.asObservable();
  }

  // Close the WebSocket connection
  closeConnection() {
    console.log('Close')
    this.socket$.complete();
  }
}
