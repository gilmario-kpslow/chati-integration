import { Component, EventEmitter, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'app-cabecalho',
  imports: [MatMenuModule, MatButtonModule, MatIconModule],
  templateUrl: './cabecalho.component.html',
  styleUrl: './cabecalho.component.css',
})
export class CabecalhoComponent {
  @Output() novo = new EventEmitter();
  @Output() atualizar = new EventEmitter();
  @Output() backup = new EventEmitter();
  @Output() restore = new EventEmitter();
  @Output() notificacoes = new EventEmitter();

  novoEvent() {
    this.novo.emit();
  }

  atualizarEvent() {
    this.atualizar.emit();
  }

  downloadBackup() {
    this.backup.emit();
  }

  uploadBackup() {
    this.restore.emit();
  }

  habilitarNotificacoes() {
    this.notificacoes.emit();
  }
}
