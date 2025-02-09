import { inject, Injectable } from '@angular/core';

import { Mensagem } from './mensagem';
import { MensagensComponent } from './mensagens.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Injectable({ providedIn: 'root' })
export class MensagemService {
  private modalService = inject(NgbModal);

  informacao(
    mensagem: string,
    titulo: string,
    detalhe: string = '',
    fn?: Function
  ) {
    this.open({ detalhe, titulo, icone: 'info', mensagem }, 'info', fn);
  }

  sucesso(
    mensagem: string,
    titulo: string = 'Sucesso!',
    detalhe: string = '',
    fn?: Function
  ) {
    this.open(
      { detalhe, titulo, icone: 'check_circle', mensagem },
      'success',
      fn
    );
  }

  atencao(
    mensagem: string,
    titulo: string = 'Atenção!',
    detalhe: string = '',
    fn?: Function
  ) {
    this.open(
      { detalhe, titulo, icone: 'report_problem', mensagem },
      'warn',
      fn
    );
  }

  error(
    mensagem: string,
    titulo: string = 'Erro',
    detalhe: string = '',
    fn?: Function
  ) {
    this.open(
      { detalhe, titulo, icone: 'report_gmailerrorred', mensagem },
      'error',
      fn
    );
  }

  open(mensagem: Mensagem, tipo: string, fn?: Function) {
    const instance = this.modalService.open(MensagensComponent, {
      ariaLabelledBy: 'modal-basic-title',
      fullscreen: false,
      centered: true,
    });

    instance.componentInstance.config = { mensagem, tipo };

    instance.result.then((resp) => {
      console.log(resp);
      if (fn) {
        fn(resp);
      }
    });

    // rf.afterClosed().subscribe(() => {
    //   if (fn) {
    //     fn();
    //   }
    // });
  }

  // open(content: TemplateRef<any>) {
  //     this.modalService
  //       .open(content, { ariaLabelledBy: 'modal-basic-title' })
  //       .result.then(
  //         (result) => {
  //           this.closeResult.set(`Closed with: ${result}`);
  //         },
  //         (reason) => {
  //           // this.closeResult.set(`Dismissed ${this.getDismissReason(reason)}`);
  //         }
  //       );
  //   }
}
