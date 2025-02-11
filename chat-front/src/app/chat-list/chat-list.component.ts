import { Component, OnInit, inject } from '@angular/core';
import { Chat } from '../core/chat.model';
import { ChatCardComponent } from '../chat-card/chat-card.component';
import { CommonModule } from '@angular/common';
import {
  MatCard,
  MatCardContent,
  MatCardHeader,
  MatCardTitle,
} from '@angular/material/card';
import { ChatService } from '../core/chat.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime } from 'rxjs';
import { AppService } from '../core/app.service';

@Component({
  selector: 'app-chat-list',
  imports: [
    ChatCardComponent,
    CommonModule,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    MatIconModule,
    MatButtonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.css',
  standalone: true,
})
export class ChatListComponent implements OnInit {
  chats: Chat[] = [];
  filtrados: Chat[] = [];
  service = inject(ChatService);
  filtro = new FormControl();
  appService = inject(AppService);

  ngOnInit(): void {
    this.filtro.valueChanges.pipe(debounceTime(250)).subscribe((v) => {
      this.filtrar();
    });

    this.appService.mudouChats.subscribe(() => {
      this.pesquisar();
    });
    this.pesquisar();
  }

  pesquisar() {
    this.service.listar().subscribe((lista) => {
      this.chats = lista.items;
      this.filtrar();
    });
  }

  filtrar() {
    this.filtrados = this.chats.filter((a) =>
      a.titulo
        .toLocaleLowerCase()
        .includes(this.filtro.value ? this.filtro.value.toLowerCase() : '')
    );
  }

  limpar() {
    this.filtro.reset();
  }
}
