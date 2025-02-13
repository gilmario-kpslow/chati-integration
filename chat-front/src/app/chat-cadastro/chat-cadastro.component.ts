import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
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
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { AppService } from '../core/app.service';

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
    MatButtonModule,
  ],
  templateUrl: './chat-cadastro.component.html',
  styleUrl: './chat-cadastro.component.css',
})
export class ChatCadastroComponent implements OnInit {
  service: ChatService = inject(ChatService);
  notify: MensagemService = inject(MensagemService);
  dialogRef = inject(MatDialogRef);
  data = inject<any>(MAT_DIALOG_DATA);
  appService = inject(AppService);

  form = new FormGroup({
    id: new FormControl(null),
    titulo: new FormControl(null, [Validators.required]),
    mensagem: new FormControl(null, [Validators.required]),
    url: new FormControl(null, [Validators.required]),
    cor: new FormControl(null, [Validators.required]),
  });

  ngOnInit(): void {
    if (this.data) {
      this.form.patchValue(this.data);
    }
  }

  temas: string[] = [
    'tema-vermelho',
    'tema-verde',
    'tema-azul',
    'tema-amarelo',
    'tema-ciano',
    'tema-fuscia',
  ];

  salvar() {
    if (this.form.valid) {
      this.service.save(this.form.value).subscribe(() => {
        this.notify.sucesso('Chat salvo com sucesso!');
        this.appService.notificarChats();
        this.dialogRef.close();
      });
      return;
    }
    this.form.markAllAsTouched();
  }
}
