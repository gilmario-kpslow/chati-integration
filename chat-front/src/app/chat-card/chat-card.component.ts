import { Component, Input, signal } from '@angular/core';
import { Chat } from '../core/chat.model';
import { CommonModule } from '@angular/common';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatMenu, MatMenuItem, MatMenuTrigger } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-chat-card',
  imports: [CommonModule, MatCard, MatCardHeader, MatCardContent, MatCardTitle, MatButtonModule, MatMenu, MatMenuTrigger, MatIcon, MatMenuItem],
  templateUrl: './chat-card.component.html',
  styleUrl: './chat-card.component.css',
})
export class ChatCardComponent {
  @Input() chat?: Chat;
  expande = false;
}
