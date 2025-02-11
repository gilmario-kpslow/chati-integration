import { Injectable } from '@angular/core';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  private socket$: WebSocketSubject<any>;

  constructor() {
    const proto = location.protocol == 'https' ? 'wss' : 'ws';

    this.socket$ = webSocket(environment.url);
  }
}
