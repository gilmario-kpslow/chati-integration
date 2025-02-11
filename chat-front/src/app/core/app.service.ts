import { EventEmitter, Inject, Injectable } from '@angular/core';

@Injectable({ providedIn: 'platform' })
export class AppService {
  mudouChats = new EventEmitter();

  notificarChats() {
    this.mudouChats.emit();
  }

  habilitarNotificacoes() {
    if (!('Notification' in window)) {
      alert('Este browser não suporta notificações de Desktop');
    }
    if (Notification.permission === 'granted') {
      this.notificar('Opa!', 'As notificações já estão ativas!');
    } else if (Notification.permission !== 'denied') {
      Notification.requestPermission(function () {
        if (Notification.permission === 'granted') {
          new Notification('Sucesso!', {
            body: 'As notificações foram ativadas!',
            icon: '/assets/imagens/logog.svg',
          });
        }
      });
    }
  }

  notificar(titulo: string, mensagem: string) {
    const img = '/assets/imagens/logog.svg';
    if (Notification.permission === 'granted') {
      new Notification(titulo, { body: mensagem, icon: img });
    }
  }
}
