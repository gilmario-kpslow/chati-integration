import { Component, OnInit, inject } from '@angular/core';
import { Chat } from '../core/chat.model';
import { ChatCardComponent } from '../chat-card/chat-card.component';
import { CommonModule } from '@angular/common';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { ChatService } from '../core/chat.service';

@Component({
  selector: 'app-chat-list',
  imports: [ChatCardComponent, CommonModule, MatCard, MatCardHeader, MatCardContent, MatCardTitle],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.css',
  standalone: true,


})
export class ChatListComponent implements OnInit {
  chats: Chat[] = [];
  service = inject(ChatService);

  ngOnInit(): void {
    this.pesquisar();
  }

  pesquisar() {
    this.service.listar().subscribe(lista => {
      console.log(lista);
      this.chats = lista.items;
    })
  }
}
