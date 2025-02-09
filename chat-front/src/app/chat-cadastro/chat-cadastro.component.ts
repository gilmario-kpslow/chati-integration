import { Component, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ChatService } from '../core/chat.service';
import { CommonModule } from '@angular/common';
import { LayoutInputComponent } from '../layout-input/layout-input.component';
import { LayoutSelectComponent } from '../layout-select/layout-select.component';
import { MensagemService } from '../mensagens/messagem.service';

@Component({
  selector: 'app-chat-cadastro',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    LayoutInputComponent,
    LayoutSelectComponent,
  ],
  templateUrl: './chat-cadastro.component.html',
  styleUrl: './chat-cadastro.component.css',
})
export class ChatCadastroComponent {
  service: ChatService = inject(ChatService);
  notify: MensagemService = inject(MensagemService);

  form = new FormGroup({
    id: new FormControl(null),
    // collectionId: new FormControl(null),
    // collectionName: new FormControl(null),
    // created: new FormControl(null),
    // updated: new FormControl(null),
    titulo: new FormControl(null, [Validators.required]),
    mensagem: new FormControl(null, [Validators.required]),
    url: new FormControl(null, [Validators.required]),
    cor: new FormControl(null, [Validators.required]),
    // topico: new FormControl(null, [Validators.required]),
  });

  temas: string[] = ['red', 'green', 'blue', 'yellow'];

  salvar() {
    if (this.form.valid) {
      this.service.save(this.form.value).subscribe(() => {
        this.notify.sucesso('Chat salvo com sucesso');
      });
      return;
    }
    this.form.markAllAsTouched();
  }
}
