import { Component, OnInit, inject } from '@angular/core';
import { Chat } from '../core/chat.model';
import { ChatCardComponent } from '../chat-card/chat-card.component';
import { CommonModule } from '@angular/common';
import {
  MatCard,
  MatCardContent,
  MatCardHeader,
  MatCardTitle,
} from '@angular/material/card';
import { ChatService } from '../core/chat.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Subscription, debounceTime } from 'rxjs';
import { AppService } from '../core/app.service';
import { WebSocketService } from '../core/websocket/websocker';
import { NotificacaoService } from '../core/notificacao.service';
import { UserService } from '../core/user.service';

@Component({
  selector: 'app-chat-list',
  imports: [
    ChatCardComponent,
    CommonModule,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    MatIconModule,
    MatButtonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.css',
  standalone: true,
})
export class ChatListComponent implements OnInit {
  chats: Chat[] = [];
  filtrados: Chat[] = [];
  service = inject(ChatService);
  filtro = new FormControl();
  appService = inject(AppService);
  webSocket = inject(WebSocketService);
  notificar = inject(NotificacaoService);
  subs?: Subscription;

  userService = inject(UserService);

  ngOnInit(): void {

    this.webSocket.event.subscribe(() => {
      this.subs?.unsubscribe();

      this.initSocket();
    });
    this.filtro.valueChanges.pipe(debounceTime(250)).subscribe((v) => {
      this.filtrar();
    });

    this.appService.mudouChats.subscribe(() => {
      this.pesquisar();
    });

    this.pesquisar();
    this.initSocket();
  }

  pesquisar() {
    this.service.listar().subscribe((lista) => {
      this.chats = lista.items;
      this.filtrar();
    });
  }

  initSocket() {
    this.subs = this.webSocket.getMessages().subscribe((mens) => {
      console.log(mens);
      if (mens.comando == 'pesquisar') {
        this.pesquisar();
      }
      if (mens.comando == 'mensagem') {
        this.notificar.notificar(mens.mensagem);
      }
    });
  }

  filtrar() {
    this.filtrados = this.chats.filter((a) =>
      a.titulo
        .toLocaleLowerCase()
        .includes(this.filtro.value ? this.filtro.value.toLowerCase() : '')
    );
  }

  limpar() {
    this.filtro.reset();
  }
}
