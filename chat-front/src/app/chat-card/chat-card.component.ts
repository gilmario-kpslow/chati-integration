import { Component, EventEmitter, inject, Input, signal } from '@angular/core';
import { Chat } from '../core/chat.model';
import { CommonModule } from '@angular/common';
import {
  MatCard,
  MatCardContent,
  MatCardHeader,
  MatCardTitle,
} from '@angular/material/card';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatMenu, MatMenuItem, MatMenuTrigger } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';
import { ChatService } from '../core/chat.service';
import { MatDialog } from '@angular/material/dialog';
import { ChatCadastroComponent } from '../chat-cadastro/chat-cadastro.component';

@Component({
  selector: 'app-chat-card',
  imports: [
    CommonModule,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    MatButtonModule,
    MatMenu,
    MatMenuTrigger,
    MatIcon,
    MatMenuItem,
  ],
  templateUrl: './chat-card.component.html',
  styleUrl: './chat-card.component.css',
})
export class ChatCardComponent {
  service = inject(ChatService);
  dialog = inject(MatDialog);
  @Input() chat?: Chat;
  expande = false;

  editar() {
    this.dialog.open(ChatCadastroComponent, { data: this.chat });
  }

  excluir() {}

  testar() {}
}
