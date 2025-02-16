import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ChatService } from '../core/chat.service';
import { MensagemService } from '../mensagens/messagem.service';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { Chat } from '../core/chat.model';
import { AppService } from '../core/app.service';
import { getHost } from '../core/host-resolve';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { NotificacaoService } from '../core/notificacao.service';

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
    MatSlideToggleModule,
    ReactiveFormsModule
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
  notificacao = inject(NotificacaoService);

  getUrl() {
    return `${getHost()}registro/executar/${this.chat.chave}`;
  }

  ativo = new FormControl();

  ngOnInit(): void {
    this.ativo.patchValue(this.chat?.ativo);
    this.ativo.valueChanges.subscribe(a => {
      if (!this.chat) {
        return;
      }
      this.service.ativar(this.chat.id).subscribe(() => {
        this.chat.ativo = a;
        this.notificacao.notificar(`Registro ${a ? 'ativado' : 'Desativado'}`);
      });
    });
  }

}
