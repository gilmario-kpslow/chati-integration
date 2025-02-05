import { Component, Input } from '@angular/core';
import { Chat } from '../core/chat.model';
import { CommonModule } from '@angular/common';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-chat-card',
  imports: [CommonModule, NgbDropdownModule],
  templateUrl: './chat-card.component.html',
  styleUrl: './chat-card.component.css',
})
export class ChatCardComponent {
  @Input() chat?: Chat;
}
