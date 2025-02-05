import { Component, inject } from '@angular/core';
import { Chat } from '../core/chat.model';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ChatService } from '../core/chat.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat-cadastro',
  imports: [ReactiveFormsModule, FormsModule, CommonModule],
  templateUrl: './chat-cadastro.component.html',
  styleUrl: './chat-cadastro.component.css',
})
export class ChatCadastroComponent {
  service: ChatService = inject(ChatService);

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
    topico: new FormControl(null, [Validators.required]),
  });

  salvar() {
    if (this.form.valid) {
      return;
    }
    this.form.markAllAsTouched();
  }
}
