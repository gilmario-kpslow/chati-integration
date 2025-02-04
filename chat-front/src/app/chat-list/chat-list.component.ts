import { Component } from '@angular/core';
import { Chat } from '../core/chat.model';
import { ChatCardComponent } from '../chat-card/chat-card.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat-list',
  imports: [ChatCardComponent, CommonModule],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.css',
})
export class ChatListComponent {
  chats: Chat[] = [
    {
      cor: 'red',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
    {
      cor: 'green',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
    {
      cor: 'blue',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
    {
      cor: 'yellow',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
    {
      cor: 'orange',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
    {
      cor: 'red',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
    {
      cor: 'red',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
    {
      cor: 'red',
      id: 'asdqwdasd',
      mensagem: 'asda',
      titulo: 'TESTE',
      topico: 'Totipco',
      url: 'http://localhost:8080',
    },
  ];
}
