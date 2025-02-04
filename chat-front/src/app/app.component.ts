import { Component } from '@angular/core';
import { CabecalhoComponent } from './cabecalho/cabecalho.component';
import { RodapeComponent } from './rodape/rodape.component';
import { ChatListComponent } from './chat-list/chat-list.component';

@Component({
  selector: 'app-root',
  imports: [CabecalhoComponent, RodapeComponent, ChatListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'chat-front';
}
