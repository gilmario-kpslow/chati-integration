import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ChatService } from '../core/chat.service';
import { MensagemService } from '../mensagens/messagem.service';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { Chat } from '../core/chat.model';
import { AppService } from '../core/app.service';
import { getHost } from '../core/host-resolve';

@Component({
  selector: 'app-chat-view',
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatButtonModule,
  ],
  templateUrl: './chat-view.component.html',
  styleUrl: './chat-view.component.css'
})
export class ChatViewComponent {

  service: ChatService = inject(ChatService);
  notify: MensagemService = inject(MensagemService);
  dialogRef = inject(MatDialogRef);
  chat = inject<Chat>(MAT_DIALOG_DATA);
  appService = inject(AppService);

  getUrl() {
    return `${getHost()}registro/executar/${this.chat.chave}`;
  }

}
