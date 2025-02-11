import { Component, EventEmitter, Output, inject } from '@angular/core';
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
import { MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-chat-cadastro',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    LayoutInputComponent,
    LayoutSelectComponent,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    MatButtonModule
  ],
  templateUrl: './chat-cadastro.component.html',
  styleUrl: './chat-cadastro.component.css',
})
export class ChatCadastroComponent {

  service: ChatService = inject(ChatService);
  notify: MensagemService = inject(MensagemService);
  dialogRef = inject(MatDialogRef);
  @Output() savedEvent = new EventEmitter();

  form = new FormGroup({
    id: new FormControl(null),
    titulo: new FormControl(null, [Validators.required]),
    mensagem: new FormControl(null, [Validators.required]),
    url: new FormControl(null, [Validators.required]),
    cor: new FormControl(null, [Validators.required]),
  });

  temas: string[] = ['red', 'green', 'blue', 'yellow'];

  salvar() {
    if (this.form.valid) {
      this.service.save(this.form.value).subscribe(() => {
        this.notify.sucesso('Chat salvo com sucesso!');
        this.savedEvent.emit();
        this.dialogRef.close();
      });
      return;
    }
    this.form.markAllAsTouched();
  }
}
