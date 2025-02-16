import { Component, EventEmitter, inject, Input, OnInit, signal } from '@angular/core';
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
import { MensagemService } from '../mensagens/messagem.service';
import { PerguntaComponent } from '../pergunta/pergunta.component';
import { AppService } from '../core/app.service';
import { getHost } from '../core/host-resolve';
import { ChatViewComponent } from '../chat-view/chat-view.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { NotificacaoService } from '../core/notificacao.service';

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
    MatSlideToggleModule,
    ReactiveFormsModule,
  ],
  templateUrl: './chat-card.component.html',
  styleUrl: './chat-card.component.css',
})
export class ChatCardComponent implements OnInit {
  service = inject(ChatService);
  mensagemService = inject(MensagemService);
  dialog = inject(MatDialog);
  @Input() chat?: Chat;
  appService = inject(AppService);
  notificacao = inject(NotificacaoService);
  ativo = new FormControl();

  ngOnInit(): void {
    this.ativo.patchValue(this.chat?.ativo);
    this.ativo.valueChanges.subscribe(a => {
      if (!this.chat) {
        return;
      }
      this.service.ativar(this.chat.id).subscribe(() => {
        if (!this.chat) {
          return;
        }
        this.chat.ativo = a;
        this.notificacao.notificar(`Registro ${a ? 'ativado' : 'Desativado'}`);
      });
    });
  }


  editar() {
    this.dialog.open(ChatCadastroComponent, { data: this.chat });
  }

  excluir() {
    this.dialog
      .open(PerguntaComponent, {
        data: {
          pergunta: 'Excluir registro',
          mensagem: 'Deseja excluir esse Registro?',
        },
      })
      .afterClosed()
      .subscribe((resp) => {
        if (resp) {
          if (!this.chat) {
            return;
          }
          this.service.delete(this.chat?.id).subscribe(() => {
            this.mensagemService.sucesso('Registro excluído com sucesso!');
            this.appService.notificarChats();
          });
        }
      });
  }

  testar() {
    if (!this.chat) {
      return;
    }

    this.service
      .executar(this.chat.chave, this.extractParametros(this.chat.mensagem))
      .subscribe();
  }

  nofiticar() {
    if (!this.chat) {
      return;
    }

    this.service
      .notificar(this.chat.id, this.extractParametros(this.chat.mensagem))
      .subscribe();
  }

  extractParametros(mensagem: string) {
    const regex = /\$\w+}/g;
    const infos = mensagem.match(regex);
    if (!infos) {
      return '';
    }
    return `?${infos
      .map((a) => a.replace('$', '').replace('{', '').replace('}', ''))
      .reduce((a, b) => `${a}=teste&${b}`)}`;
  }

  getUrl() {
    return `${getHost()}registro/executar/${this.chat?.chave}`;
  }

  ver() {
    this.dialog
      .open(ChatViewComponent, {
        data: this.chat,
        width: `50%`
      });
  }
}
