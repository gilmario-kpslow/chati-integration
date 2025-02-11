import { HttpErrorResponse } from '@angular/common/http';
import { ErrorHandler, Injectable, NgZone, inject } from '@angular/core';
import { Router } from '@angular/router';
import { MensagemService } from './mensagens/messagem.service';
import { NotificacaoService } from './core/notificacao.service';
@Injectable({ providedIn: 'root' })
export class AppErrorHandler extends ErrorHandler {
  router = inject(Router);
  mensagem = inject(MensagemService);
  notificador = inject(NotificacaoService);
  constructor(private zone: NgZone) {
    super();
  }

  override handleError(errorResponse: HttpErrorResponse | any) {
    if (errorResponse instanceof HttpErrorResponse) {
      const error = errorResponse.error ? errorResponse.error : undefined;
      console.log(error);
      this.zone.run(() => {
        switch (errorResponse.status) {
          case 0:
            this.notificador.notificar(
              'O Servidor não respondeu. A api parece indisponível no momento.'
            );
            break;
          case 401:
            this.router.navigate(['/auth']);
            this.mensagem.error(
              'Não autorizado',
              'Erro',
              'Usuário ou senha incorretos'
            );
            break;
          case 403:
            this.notificador.notificar(
              'Usuário não está autorizado a acessar este recurso.'
            );
            break;
          case 406:
            this.notificador.notificar(error.mensagem);
            break;
          case 500:
            this.notificador.notificar(
              'Erro interno no servidor. Entre em contato com o suporte.'
            );
            break;
          case 503:
            this.notificador.notificar(
              'Erro interno no servidor. Entre em contato com o suporte.'
            );
            break;
          default:
            this.notificador.notificar(
              'Erro interno por favor, tente novamente mais tarde!'
            );
            break;
        }
      });
    }
    super.handleError(errorResponse);
  }
}
