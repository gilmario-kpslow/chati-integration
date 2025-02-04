import { Component, Input } from '@angular/core';
import { Chat } from '../core/chat.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat-card',
  imports: [CommonModule],
  templateUrl: './chat-card.component.html',
  styleUrl: './chat-card.component.css',
})
export class ChatCardComponent {
  @Input() chat?: Chat;
}
