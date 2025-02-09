import { Component, Inject } from '@angular/core';

import { MensagemConfig } from './mensagem-config';

@Component({
  selector: 'app-mensagens',
  templateUrl: './mensagens.component.html',
  styleUrls: ['./mensagens.component.css'],
  standalone: true,
  imports: [],
})
export class MensagensComponent {
  config?: MensagemConfig;
  constructor() {}

  close() {}
}
