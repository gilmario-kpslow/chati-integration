import {
  Component,
  inject,
} from '@angular/core';
import { CabecalhoComponent } from './cabecalho/cabecalho.component';
import { RodapeComponent } from './rodape/rodape.component';
import { ChatListComponent } from './chat-list/chat-list.component';
import { ChatCadastroComponent } from './chat-cadastro/chat-cadastro.component';
import { MensagemService } from './mensagens/messagem.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-root',
  imports: [
    CabecalhoComponent,
    RodapeComponent,
    ChatListComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'chat-front';

  private modalService = inject(MatDialog);
  private mensagemService = inject(MensagemService);

  openCadastro() {
    this.modalService.open(ChatCadastroComponent, {});
  }

  close() {

  }

  atualizar() {
    this.mensagemService.sucesso("Registro salvo com sucesso!");
  }
}
